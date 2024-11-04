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

public class ActualizarDVDForm extends JFrame {
    private JComboBox<String> comboDVDs;
    private JTextField txtTitulo;
    private JTextField txtDirector;
    private JTextField txtGenero;
    private JTextField txtDuracion;
    private JTextField txtUnidades;

    public ActualizarDVDForm() {
        setTitle("Actualizar DVD");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        // Crear componentes del formulario
        JLabel lblSeleccionarDVD = new JLabel("Seleccionar DVD:");
        comboDVDs = new JComboBox<>();
        cargarDVDs();

        JLabel lblTitulo = new JLabel("Título:");
        txtTitulo = new JTextField();
        JLabel lblDirector = new JLabel("Director:");
        txtDirector = new JTextField();
        JLabel lblGenero = new JLabel("Género:");
        txtGenero = new JTextField();
        JLabel lblDuracion = new JLabel("Duración (min):");
        txtDuracion = new JTextField();
        JLabel lblUnidades = new JLabel("Unidades Disponibles:");
        txtUnidades = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        // Agregar componentes al frame
        add(lblSeleccionarDVD);
        add(comboDVDs);
        add(lblTitulo);
        add(txtTitulo);
        add(lblDirector);
        add(txtDirector);
        add(lblGenero);
        add(txtGenero);
        add(lblDuracion);
        add(txtDuracion);
        add(lblUnidades);
        add(txtUnidades);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        // Acción para el botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDVD();
            }
        });

        // Acción para seleccionar un DVD
        comboDVDs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosDVD();
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }

    private void cargarDVDs() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT DVDID, TituloDVD FROM DVDs";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String dvdID = resultSet.getString("DVDID");
                    String tituloDVD = resultSet.getString("TituloDVD");
                    comboDVDs.addItem(dvdID + " - " + tituloDVD);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los DVDs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosDVD() {
        String seleccionado = (String) comboDVDs.getSelectedItem();
        if (seleccionado != null) {
            String dvdID = seleccionado.split(" - ")[0];
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM DVDs WHERE DVDID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, dvdID);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            txtTitulo.setText(resultSet.getString("TituloDVD"));
                            txtDirector.setText(resultSet.getString("Director"));
                            txtGenero.setText(resultSet.getString("Genero"));
                            txtDuracion.setText(resultSet.getString("Duracion"));
                            txtUnidades.setText(resultSet.getString("UnidadesDisponibles"));
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar los datos del DVD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarDVD() {
        String seleccionado = (String) comboDVDs.getSelectedItem();
        if (seleccionado != null) {
            String dvdID = seleccionado.split(" - ")[0];
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "UPDATE DVDs SET TituloDVD = ?, Director = ?, Genero = ?, Duracion = ?, UnidadesDisponibles = ? WHERE DVDID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, txtTitulo.getText());
                    statement.setString(2, txtDirector.getText());
                    statement.setString(3, txtGenero.getText());
                    statement.setInt(4, Integer.parseInt(txtDuracion.getText()));
                    statement.setInt(5, Integer.parseInt(txtUnidades.getText()));
                    statement.setString(6, dvdID);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "DVD actualizado exitosamente.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al actualizar el DVD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
