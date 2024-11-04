package mostrar;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarAutorForm extends JFrame {
    private JTextField txtNombre;
    private JTextField txtNacionalidad;

    public MostrarAutorForm(String id) {
        setTitle("Mostrar Autor");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        txtNombre = new JTextField();
        txtNacionalidad = new JTextField();
        JButton btnCerrar = new JButton("Cerrar");

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Nacionalidad:"));
        add(txtNacionalidad);
        add(new JLabel()); // Espacio vacío
        add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());

        cargarDatosAutor(id);

        setVisible(true);
    }

    private void cargarDatosAutor(String id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Autores WHERE IDAutor = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        txtNombre.setText(resultSet.getString("NombreAutor"));
                        txtNacionalidad.setText(resultSet.getString("Nacionalidad"));
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró el autor con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del autor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

