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

public class ActualizarAutorForm extends JFrame {
    private JComboBox<String> comboAutores;
    private JTextField txtNombre;
    private JTextField txtNacionalidad;

    public ActualizarAutorForm() {
        setTitle("Actualizar Autor");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        comboAutores = new JComboBox<>();
        cargarAutores();

        txtNombre = new JTextField();
        txtNacionalidad = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Seleccionar Autor:"));
        add(comboAutores);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Nacionalidad:"));
        add(txtNacionalidad);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        comboAutores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosAutor();
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarAutor();
            }
        });

        setVisible(true);
    }

    private void cargarAutores() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT IDAutor, NombreAutor FROM Autores";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("IDAutor");
                    String nombre = resultSet.getString("NombreAutor");
                    comboAutores.addItem(id + " - " + nombre);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los autores.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosAutor() {
        String seleccionado = (String) comboAutores.getSelectedItem();
        if (seleccionado != null) {
            String id = seleccionado.split(" - ")[0];
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM Autores WHERE IDAutor = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, id);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            txtNombre.setText(resultSet.getString("NombreAutor"));
                            txtNacionalidad.setText(resultSet.getString("Nacionalidad"));
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar los datos del autor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarAutor() {
        String seleccionado = (String) comboAutores.getSelectedItem();
        if (seleccionado != null) {
            String id = seleccionado.split(" - ")[0];
            String nombre = txtNombre.getText().trim();
            String nacionalidad = txtNacionalidad.getText().trim();

            if (nombre.isEmpty() || nacionalidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "UPDATE Autores SET NombreAutor = ?, Nacionalidad = ? WHERE IDAutor = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, nombre);
                    statement.setString(2, nacionalidad);
                    statement.setString(3, id);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Autor actualizado exitosamente.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al actualizar el autor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

