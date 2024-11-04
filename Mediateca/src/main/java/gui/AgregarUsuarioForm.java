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

public class AgregarUsuarioForm extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDUI;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtEmail;

    public AgregarUsuarioForm() {
        setTitle("Agregar Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtDUI = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtEmail = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Apellido:"));
        add(txtApellido);
        add(new JLabel("DUI:"));
        add(txtDUI);
        add(new JLabel("Dirección:"));
        add(txtDireccion);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(new JLabel("Email:"));
        add(txtEmail);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarUsuario();
            }
        });

        setVisible(true);
    }

    private void agregarUsuario() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String dui = txtDUI.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || dui.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (emailExiste(connection, email)) {
                JOptionPane.showMessageDialog(this, "El correo electrónico ya está en uso. Por favor, intente con otro.", "Correo en uso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (duiExiste(connection, dui)) {
                JOptionPane.showMessageDialog(this, "El DUI ya está en uso. Por favor, intente con otro.", "DUI en uso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String sql = "INSERT INTO Clientes (IDCliente, NombreCliente, ApellidoCliente, DUICliente, DireccionCliente, TelefonoCliente, EmailCliente) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                String clienteID = IdentificadorGenerator.generarIdentificador(connection, "CLI");
                statement.setString(1, clienteID);
                statement.setString(2, nombre);
                statement.setString(3, apellido);
                statement.setString(4, dui);
                statement.setString(5, direccion);
                statement.setString(6, telefono);
                statement.setString(7, email);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.");
                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean emailExiste(Connection connection, String email) throws SQLException {
        String sql = "SELECT 1 FROM Clientes WHERE EmailCliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private boolean duiExiste(Connection connection, String dui) throws SQLException {
        String sql = "SELECT 1 FROM Clientes WHERE DUICliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dui);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}





