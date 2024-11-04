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

public class AgregarCDForm extends JFrame {
    private JTextField txtTitulo, txtArtista, txtGenero, txtDuracion, txtCanciones, txtUnidades;

    public AgregarCDForm() {
        setTitle("Agregar CD de Audio");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        txtTitulo = new JTextField();
        txtArtista = new JTextField();
        txtGenero = new JTextField();
        txtDuracion = new JTextField();
        txtCanciones = new JTextField();
        txtUnidades = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Artista:"));
        add(txtArtista);
        add(new JLabel("Género:"));
        add(txtGenero);
        add(new JLabel("Duración (min):"));
        add(txtDuracion);
        add(new JLabel("Número de Canciones:"));
        add(txtCanciones);
        add(new JLabel("Unidades Disponibles:"));
        add(txtUnidades);
        add(new JLabel());
        add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    try (Connection connection = DatabaseConnection.getConnection()) {
                        agregarCD(connection, txtTitulo.getText(), txtArtista.getText(), txtGenero.getText(), Integer.parseInt(txtDuracion.getText()), Integer.parseInt(txtCanciones.getText()), Integer.parseInt(txtUnidades.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al agregar el CD de Audio.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        setVisible(true);
    }

    private boolean validarCampos() {
        if (txtTitulo.getText().trim().isEmpty() || txtArtista.getText().trim().isEmpty() || txtGenero.getText().trim().isEmpty() || txtDuracion.getText().trim().isEmpty() || txtCanciones.getText().trim().isEmpty() || txtUnidades.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtDuracion.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "La duración debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtCanciones.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "El número de canciones debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean cdExiste(Connection connection, String titulo, String artista) throws SQLException {
        String sql = "SELECT 1 FROM CDsAudio WHERE TituloCD = ? AND Artista = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, titulo);
            statement.setString(2, artista);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void agregarCD(Connection connection, String titulo, String artista, String genero, int duracion, int numeroCanciones, int unidadesDisponibles) throws SQLException {
        if (cdExiste(connection, titulo, artista)) {
            JOptionPane.showMessageDialog(null, "El CD de Audio ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String sql = "INSERT INTO CDsAudio (CDID, TituloCD, Artista, Genero, Duracion, NumeroCanciones, UnidadesDisponibles) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                String cdID = IdentificadorGenerator.generarIdentificador(connection, "CDA");
                statement.setString(1, cdID);
                statement.setString(2, titulo);
                statement.setString(3, artista);
                statement.setString(4, genero);
                statement.setInt(5, duracion);
                statement.setInt(6, numeroCanciones);
                statement.setInt(7, unidadesDisponibles);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "CD de Audio agregado exitosamente.");
                dispose();
            }
        }
    }
}
