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

public class ActualizarRevistaForm extends JFrame {
    private JComboBox<String> comboRevistas;
    private JTextField txtTitulo;
    private JTextField txtEditorial;
    private JTextField txtPeriodicidad;
    private JTextField txtFecha;
    private JTextField txtUnidades;

    public ActualizarRevistaForm() {
        setTitle("Actualizar Revista");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        // Crear componentes del formulario
        JLabel lblSeleccionarRevista = new JLabel("Seleccionar Revista:");
        comboRevistas = new JComboBox<>();
        cargarRevistas();

        JLabel lblTitulo = new JLabel("Título:");
        txtTitulo = new JTextField();
        JLabel lblEditorial = new JLabel("Editorial:");
        txtEditorial = new JTextField();
        JLabel lblPeriodicidad = new JLabel("Periodicidad:");
        txtPeriodicidad = new JTextField();
        JLabel lblFecha = new JLabel("Fecha de Publicación:");
        txtFecha = new JTextField();
        JLabel lblUnidades = new JLabel("Unidades Disponibles:");
        txtUnidades = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        // Agregar componentes al frame
        add(lblSeleccionarRevista);
        add(comboRevistas);
        add(lblTitulo);
        add(txtTitulo);
        add(lblEditorial);
        add(txtEditorial);
        add(lblPeriodicidad);
        add(txtPeriodicidad);
        add(lblFecha);
        add(txtFecha);
        add(lblUnidades);
        add(txtUnidades);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        // Acción para el botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarRevista();
            }
        });

        // Acción para seleccionar una revista
        comboRevistas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosRevista();
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }

    private void cargarRevistas() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT RevistaID, TituloRevista FROM Revistas";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String revistaID = resultSet.getString("RevistaID");
                    String tituloRevista = resultSet.getString("TituloRevista");
                    comboRevistas.addItem(revistaID + " - " + tituloRevista);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las revistas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosRevista() {
        String seleccionado = (String) comboRevistas.getSelectedItem();
        if (seleccionado != null) {
            String revistaID = seleccionado.split(" - ")[0];
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM Revistas WHERE RevistaID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, revistaID);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            txtTitulo.setText(resultSet.getString("TituloRevista"));
                            txtEditorial.setText(resultSet.getString("EditorialID"));
                            txtPeriodicidad.setText(resultSet.getString("Periodicidad"));
                            txtFecha.setText(resultSet.getString("FechaPublicacion"));
                            txtUnidades.setText(resultSet.getString("UnidadesDisponibles"));
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar los datos de la revista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarRevista() {
        String seleccionado = (String) comboRevistas.getSelectedItem();
        if (seleccionado != null) {
            String revistaID = seleccionado.split(" - ")[0];
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "UPDATE Revistas SET TituloRevista = ?, EditorialID = ?, Periodicidad = ?, FechaPublicacion = ?, UnidadesDisponibles = ? WHERE RevistaID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, txtTitulo.getText());
                    statement.setString(2, txtEditorial.getText());
                    statement.setString(3, txtPeriodicidad.getText());
                    statement.setDate(4, java.sql.Date.valueOf(txtFecha.getText()));
                    statement.setInt(5, Integer.parseInt(txtUnidades.getText()));
                    statement.setString(6, revistaID);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Revista actualizada exitosamente.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al actualizar la revista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

