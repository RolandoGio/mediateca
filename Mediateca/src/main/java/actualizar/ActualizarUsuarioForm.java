package actualizar;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActualizarUsuarioForm extends JFrame {
    private JComboBox<String> comboUsuarios;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDUI;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtEmail;

    public ActualizarUsuarioForm() {
        setTitle("Actualizar Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));

        comboUsuarios = new JComboBox<>();
        cargarUsuarios();

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtDUI = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtEmail = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Seleccionar Usuario:"));
        add(comboUsuarios);
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

        comboUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosUsuario();
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarUsuario();
            }
        });

        setVisible(true);
    }

    private void cargarUsuarios() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT IDCliente, NombreCliente FROM Clientes";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("IDCliente");
                    String nombre = resultSet.getString("NombreCliente");
                    comboUsuarios.addItem(id + " - " + nombre);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosUsuario() {
        String seleccionado = (String) comboUsuarios.getSelectedItem();
        if (seleccionado != null) {
            String id = seleccionado.split(" - ")[0];
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM Clientes WHERE IDCliente = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, id);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            txtNombre.setText(resultSet.getString("NombreCliente"));
                            txtApellido.setText(resultSet.getString("ApellidoCliente"));
                            txtDUI.setText(resultSet.getString("DUICliente"));
                            txtDireccion.setText(resultSet.getString("DireccionCliente"));
                            txtTelefono.setText(resultSet.getString("TelefonoCliente"));
                            txtEmail.setText(resultSet.getString("EmailCliente"));
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar los datos del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarUsuario() {
        String seleccionado = (String) comboUsuarios.getSelectedItem();
        if (seleccionado != null) {
            String id = seleccionado.split(" - ")[0];
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
                String sql = "UPDATE Clientes SET NombreCliente = ?, ApellidoCliente = ?, DUICliente = ?, DireccionCliente = ?, TelefonoCliente = ?, EmailCliente = ? WHERE IDCliente = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, nombre);
                    statement.setString(2, apellido);
                    statement.setString(3, dui);
                    statement.setString(4, direccion);
                    statement.setString(5, telefono);
                    statement.setString(6, email);
                    statement.setString(7, id);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


