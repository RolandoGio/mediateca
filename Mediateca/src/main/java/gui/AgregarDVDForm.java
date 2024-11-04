package gui;

import database.DatabaseConnection;
import model.IdentificadorGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgregarDVDForm extends JFrame {
    private JTextField txtTitulo, txtDirector, txtGenero, txtDuracion, txtUnidades;

    public AgregarDVDForm() {
        setTitle("Agregar DVD");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        txtTitulo = new JTextField();
        txtDirector = new JTextField();
        txtGenero = new JTextField();
        txtDuracion = new JTextField();
        txtUnidades = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Director:"));
        add(txtDirector);
        add(new JLabel("Género:"));
        add(txtGenero);
        add(new JLabel("Duración (min):"));
        add(txtDuracion);
        add(new JLabel("Unidades Disponibles:"));
        add(txtUnidades);
        add(new JLabel());
        add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    try (Connection connection = DatabaseConnection.getConnection()) {
                        agregarDVD(connection, txtTitulo.getText(), txtDirector.getText(), txtGenero.getText(), Integer.parseInt(txtDuracion.getText()), Integer.parseInt(txtUnidades.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al agregar el DVD.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        setVisible(true);
    }

    private boolean validarCampos() {
        if (txtTitulo.getText().trim().isEmpty() || txtDirector.getText().trim().isEmpty() || txtGenero.getText().trim().isEmpty() || txtDuracion.getText().trim().isEmpty() || txtUnidades.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtDuracion.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "La duración debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean dvdExiste(Connection connection, String titulo, String director) throws SQLException {
        String sql = "SELECT 1 FROM DVDs WHERE TituloDVD = ? AND Director = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, titulo);
            statement.setString(2, director);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void agregarDVD(Connection connection, String titulo, String director, String genero, int duracion, int unidadesDisponibles) throws SQLException {
        if (dvdExiste(connection, titulo, director)) {
            JOptionPane.showMessageDialog(null, "El DVD ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String sql = "INSERT INTO DVDs (DVDID, TituloDVD, Director, Genero, Duracion, UnidadesDisponibles) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                String dvdID = IdentificadorGenerator.generarIdentificador(connection, "DVD");
                statement.setString(1, dvdID);
                statement.setString(2, titulo);
                statement.setString(3, director);
                statement.setString(4, genero);
                statement.setInt(5, duracion);
                statement.setInt(6, unidadesDisponibles);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "DVD agregado exitosamente.");
                dispose();
            }
        }
    }
}

