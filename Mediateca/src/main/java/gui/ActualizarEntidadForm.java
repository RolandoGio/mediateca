package gui;

import database.DatabaseConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActualizarEntidadForm extends JFrame {
    private static final Logger logger = LogManager.getLogger(ActualizarEntidadForm.class);
    private JComboBox<String> comboEntidades;
    private JTextField txtNombre;
    private JTextField txtDetalles;
    private JButton btnActualizar;

    public ActualizarEntidadForm() {
        setTitle("Actualizar Entidad");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        comboEntidades = new JComboBox<>(new String[]{"Autor", "CD", "DVD", "Editorial", "Libro", "Prestamo", "Revista", "Usuario"});
        txtNombre = new JTextField();
        txtDetalles = new JTextField();
        btnActualizar = new JButton("Actualizar");

        add(new JLabel("Seleccionar Entidad:"));
        add(comboEntidades);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Detalles:"));
        add(txtDetalles);
        add(new JLabel());
        add(btnActualizar);

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEntidad();
            }
        });

        setVisible(true);
    }

    private void actualizarEntidad() {
        String entidadSeleccionada = (String) comboEntidades.getSelectedItem();
        String nombre = txtNombre.getText().trim();
        String detalles = txtDetalles.getText().trim();

        if (nombre.isEmpty() || detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "";

            switch (entidadSeleccionada) {
                case "Autor":
                    sql = "UPDATE Autores SET NombreAutor = ?, Nacionalidad = ? WHERE NombreAutor = ?";
                    break;
                case "CD":
                    sql = "UPDATE CD SET Detalles = ? WHERE Nombre = ?";
                    break;
                case "DVD":
                    sql = "UPDATE DVD SET Detalles = ? WHERE Nombre = ?";
                    break;
                case "Editorial":
                    sql = "UPDATE Editorial SET Detalles = ? WHERE Nombre = ?";
                    break;
                case "Libro":
                    sql = "UPDATE Libro SET Detalles = ? WHERE Nombre = ?";
                    break;
                case "Préstamo":
                    sql = "UPDATE Prestamo SET Detalles = ? WHERE IDPrestamo = ?";
                    break;
                case "Revista":
                    sql = "UPDATE Revista SET Detalles = ? WHERE Nombre = ?";
                    break;
                case "Usuario":
                    sql = "UPDATE Usuario SET Detalles = ? WHERE Nombre = ?";
                    break;
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, detalles);
                statement.setString(2, nombre);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, entidadSeleccionada + " actualizado exitosamente.");
            }
        } catch (SQLException ex) {
            logger.error("Error al actualizar " + entidadSeleccionada, ex);
            JOptionPane.showMessageDialog(this, "Error al actualizar " + entidadSeleccionada, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ActualizarEntidadForm::new);
    }
}
