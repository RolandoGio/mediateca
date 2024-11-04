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

public class ActualizarEditorialForm extends JFrame {
    private JComboBox<String> comboEditoriales;
    private JTextField txtNombre;
    private JTextField txtPais;

    public ActualizarEditorialForm() {
        setTitle("Actualizar Editorial");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        comboEditoriales = new JComboBox<>();
        cargarEditoriales();

        txtNombre = new JTextField();
        txtPais = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Seleccionar Editorial:"));
        add(comboEditoriales);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("País:"));
        add(txtPais);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        comboEditoriales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosEditorial();
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEditorial();
            }
        });

        setVisible(true);
    }

    private void cargarEditoriales() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT EditorialID, NombreEditorial FROM Editoriales";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("EditorialID");
                    String nombre = resultSet.getString("NombreEditorial");
                    comboEditoriales.addItem(id + " - " + nombre);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las editoriales.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosEditorial() {
        String seleccionado = (String) comboEditoriales.getSelectedItem();
        if (seleccionado != null) {
            String id = seleccionado.split(" - ")[0];
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM Editoriales WHERE EditorialID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, id);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            txtNombre.setText(resultSet.getString("NombreEditorial"));
                            txtPais.setText(resultSet.getString("PaisEditorial"));
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar los datos de la editorial.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarEditorial() {
        String seleccionado = (String) comboEditoriales.getSelectedItem();
        if (seleccionado != null) {
            String id = seleccionado.split(" - ")[0];
            String nombre = txtNombre.getText().trim();
            String pais = txtPais.getText().trim();

            if (nombre.isEmpty() || pais.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "UPDATE Editoriales SET NombreEditorial = ?, PaisEditorial = ? WHERE EditorialID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, nombre);
                    statement.setString(2, pais);
                    statement.setString(3, id);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Editorial actualizada exitosamente.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al actualizar la editorial.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

