package gui;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrarPrestamoForm extends JFrame {
    private JComboBox<String> comboIDPrestamo;

    public BorrarPrestamoForm() {
        setTitle("Borrar Préstamo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        comboIDPrestamo = new JComboBox<>();
        JButton btnBorrar = new JButton("Borrar");

        add(new JLabel("ID del Préstamo:"));
        add(comboIDPrestamo);
        add(new JLabel()); // Espacio vacío
        add(btnBorrar);

        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarPrestamo();
            }
        });

        cargarPrestamos();

        setVisible(true);
    }

    private void cargarPrestamos() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT IDPrestamo FROM Prestamos";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    comboIDPrestamo.addItem(resultSet.getString("IDPrestamo"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los préstamos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrarPrestamo() {
        String idPrestamo = (String) comboIDPrestamo.getSelectedItem();

        if (idPrestamo == null || idPrestamo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un ID de Préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Prestamos WHERE IDPrestamo = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, idPrestamo);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Préstamo borrado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el préstamo con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al borrar el préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

