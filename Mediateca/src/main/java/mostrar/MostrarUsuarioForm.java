package mostrar;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarUsuarioForm extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDUI;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtEmail;

    public MostrarUsuarioForm(String id) {
        setTitle("Mostrar Usuario");
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
        JButton btnCerrar = new JButton("Cerrar");

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
        add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());

        cargarDatosUsuario(id);

        setVisible(true);
    }

    private void cargarDatosUsuario(String id) {
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
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró el usuario con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

