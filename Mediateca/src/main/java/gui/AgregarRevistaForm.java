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

public class AgregarRevistaForm extends JFrame {
    private JTextField txtTitulo, txtEditorial, txtPeriodicidad, txtFecha, txtUnidades;

    public AgregarRevistaForm() {
        setTitle("Agregar Revista");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        txtTitulo = new JTextField();
        txtEditorial = new JTextField();
        txtPeriodicidad = new JTextField();
        txtFecha = new JTextField();
        txtUnidades = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Editorial:"));
        add(txtEditorial);
        add(new JLabel("Periodicidad:"));
        add(txtPeriodicidad);
        add(new JLabel("Fecha de Publicación (YYYY-MM-DD):"));
        add(txtFecha);
        add(new JLabel("Unidades Disponibles:"));
        add(txtUnidades);
        add(new JLabel());
        add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    try (Connection connection = DatabaseConnection.getConnection()) {
                        agregarRevista(connection, txtTitulo.getText(), txtEditorial.getText(), txtPeriodicidad.getText(), txtFecha.getText(), Integer.parseInt(txtUnidades.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al agregar la revista.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        setVisible(true);
    }

    private boolean validarCampos() {
        if (txtTitulo.getText().trim().isEmpty() || txtEditorial.getText().trim().isEmpty() || txtPeriodicidad.getText().trim().isEmpty() || txtFecha.getText().trim().isEmpty() || txtUnidades.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtFecha.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(null, "La fecha de publicación debe tener el formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean revistaExiste(Connection connection, String titulo, String editorial) throws SQLException {
        String sql = "SELECT 1 FROM Revistas WHERE TituloRevista = ? AND EditorialID = (SELECT EditorialID FROM Editoriales WHERE NombreEditorial = ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, titulo);
            statement.setString(2, editorial);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void agregarRevista(Connection connection, String titulo, String editorial, String periodicidad, String fechaPublicacion, int unidadesDisponibles) throws SQLException {
        if (revistaExiste(connection, titulo, editorial)) {
            JOptionPane.showMessageDialog(null, "La revista ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String editorialID = obtenerOcrearEditorial(connection, editorial);
            String sql = "INSERT INTO Revistas (RevistaID, TituloRevista, EditorialID, Periodicidad, FechaPublicacion, UnidadesDisponibles) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                String revistaID = IdentificadorGenerator.generarIdentificador(connection, "REV");
                statement.setString(1, revistaID);
                statement.setString(2, titulo);
                statement.setString(3, editorialID);
                statement.setString(4, periodicidad);
                statement.setDate(5, java.sql.Date.valueOf(fechaPublicacion));
                statement.setInt(6, unidadesDisponibles);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Revista agregada exitosamente.");
                dispose();
            }
        }
    }

    private String obtenerOcrearEditorial(Connection connection, String nombre) throws SQLException {
        String editorialID = null;
        String sql = "SELECT EditorialID FROM Editoriales WHERE NombreEditorial = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    editorialID = resultSet.getString("EditorialID");
                } else {
                    editorialID = IdentificadorGenerator.generarIdentificador(connection, "EDI");
                    crearEditorial(connection, editorialID, nombre, "País de la Editorial");
                }
            }
        }
        return editorialID;
    }

    private void crearEditorial(Connection connection, String editorialID, String nombre, String pais) throws SQLException {
        String sql = "INSERT INTO Editoriales (EditorialID, NombreEditorial, PaisEditorial) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, editorialID);
            statement.setString(2, nombre);
            statement.setString(3, pais);
            statement.executeUpdate();
        }
    }
}

