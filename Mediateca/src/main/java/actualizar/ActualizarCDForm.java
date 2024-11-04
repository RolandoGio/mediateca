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

public class ActualizarCDForm extends JFrame {
    private JComboBox<String> comboCDs;
    private JTextField txtTitulo;
    private JTextField txtArtista;
    private JTextField txtGenero;
    private JTextField txtDuracion;
    private JTextField txtCanciones;
    private JTextField txtUnidades;

    public ActualizarCDForm() {
        setTitle("Actualizar CD de Audio");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));

        // Crear componentes del formulario
        JLabel lblSeleccionarCD = new JLabel("Seleccionar CD:");
        comboCDs = new JComboBox<>();
        cargarCDs();

        JLabel lblTitulo = new JLabel("Título:");
        txtTitulo = new JTextField();
        JLabel lblArtista = new JLabel("Artista:");
        txtArtista = new JTextField();
        JLabel lblGenero = new JLabel("Género:");
        txtGenero = new JTextField();
        JLabel lblDuracion = new JLabel("Duración (min):");
        txtDuracion = new JTextField();
        JLabel lblCanciones = new JLabel("Número de Canciones:");
        txtCanciones = new JTextField();
        JLabel lblUnidades = new JLabel("Unidades Disponibles:");
        txtUnidades = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        // Agregar componentes al frame
        add(lblSeleccionarCD);
        add(comboCDs);
        add(lblTitulo);
        add(txtTitulo);
        add(lblArtista);
        add(txtArtista);
        add(lblGenero);
        add(txtGenero);
        add(lblDuracion);
        add(txtDuracion);
        add(lblCanciones);
        add(txtCanciones);
        add(lblUnidades);
        add(txtUnidades);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        // Acción para el botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCD();
            }
        });

        // Acción para seleccionar un CD
        comboCDs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosCD();
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }

    private void cargarCDs() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT CDID, TituloCD FROM CDsAudio";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String cdID = resultSet.getString("CDID");
                    String tituloCD = resultSet.getString("TituloCD");
                    comboCDs.addItem(cdID + " - " + tituloCD);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los CDs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosCD() {
        String seleccionado = (String) comboCDs.getSelectedItem();
        if (seleccionado != null) {
            String cdID = seleccionado.split(" - ")[0];
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM CDsAudio WHERE CDID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, cdID);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            txtTitulo.setText(resultSet.getString("TituloCD"));
                            txtArtista.setText(resultSet.getString("Artista"));
                            txtGenero.setText(resultSet.getString("Genero"));
                            txtDuracion.setText(resultSet.getString("Duracion"));
                            txtCanciones.setText(resultSet.getString("NumeroCanciones"));
                            txtUnidades.setText(resultSet.getString("UnidadesDisponibles"));
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar los datos del CD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarCD() {
        String seleccionado = (String) comboCDs.getSelectedItem();
        if (seleccionado != null) {
            String cdID = seleccionado.split(" - ")[0];
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "UPDATE CDsAudio SET TituloCD = ?, Artista = ?, Genero = ?, Duracion = ?, NumeroCanciones = ?, UnidadesDisponibles = ? WHERE CDID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, txtTitulo.getText());
                    statement.setString(2, txtArtista.getText());
                    statement.setString(3, txtGenero.getText());
                    statement.setInt(4, Integer.parseInt(txtDuracion.getText()));
                    statement.setInt(5, Integer.parseInt(txtCanciones.getText()));
                    statement.setInt(6, Integer.parseInt(txtUnidades.getText()));
                    statement.setString(7, cdID);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "CD actualizado exitosamente.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al actualizar el CD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


