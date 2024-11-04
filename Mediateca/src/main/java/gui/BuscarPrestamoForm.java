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

public class BuscarPrestamoForm extends JFrame {
    private JComboBox<String> comboIDPrestamo;

    public BuscarPrestamoForm() {
        setTitle("Buscar Préstamo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        comboIDPrestamo = new JComboBox<>();
        JButton btnBuscar = new JButton("Buscar");

        add(new JLabel("ID del Préstamo:"));
        add(comboIDPrestamo);
        add(new JLabel()); // Espacio vacío
        add(btnBuscar);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPrestamo();
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

    private void buscarPrestamo() {
        String idPrestamo = (String) comboIDPrestamo.getSelectedItem();

        if (idPrestamo == null || idPrestamo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un ID de Préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Prestamos WHERE IDPrestamo = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, idPrestamo);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String idCliente = resultSet.getString("IDCliente");
                        String cdID = resultSet.getString("CDID");
                        String dvdID = resultSet.getString("DVDID");
                        String libroID = resultSet.getString("LibroID");
                        String revistaID = resultSet.getString("RevistaID");
                        String fechaPrestamo = resultSet.getString("FechaPrestamo");
                        String fechaDevolucion = resultSet.getString("FechaDevolucion");

                        JOptionPane.showMessageDialog(this, "Préstamo encontrado:\n" +
                                "ID Cliente: " + idCliente + "\n" +
                                "ID CD: " + cdID + "\n" +
                                "ID DVD: " + dvdID + "\n" +
                                "ID Libro: " + libroID + "\n" +
                                "ID Revista: " + revistaID + "\n" +
                                "Fecha de Préstamo: " + fechaPrestamo + "\n" +
                                "Fecha de Devolución: " + fechaDevolucion);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró el préstamo con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al buscar el préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
