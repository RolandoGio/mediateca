package mostrar;



import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarCDForm extends JFrame {
    private JTextField txtTitulo;
    private JTextField txtArtista;
    private JTextField txtGenero;
    private JTextField txtDuracion;
    private JTextField txtCanciones;
    private JTextField txtUnidades;

    public MostrarCDForm(String id) {
        setTitle("Mostrar CD de Audio");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        txtTitulo = new JTextField();
        txtArtista = new JTextField();
        txtGenero = new JTextField();
        txtDuracion = new JTextField();
        txtCanciones = new JTextField();
        txtUnidades = new JTextField();
        JButton btnCerrar = new JButton("Cerrar");

        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Artista:"));
        add(txtArtista);
        add(new JLabel("Género:"));
        add(txtGenero);
        add(new JLabel("Duración (min):"));
        add(txtDuracion);
        add(new JLabel("Número de Canciones:"));
        add(txtCanciones);
        add(new JLabel("Unidades Disponibles:"));
        add(txtUnidades);
        add(new JLabel()); // Espacio vacío
        add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());

        cargarDatosCD(id);

        setVisible(true);
    }

    private void cargarDatosCD(String id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM CDsAudio WHERE CDID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        txtTitulo.setText(resultSet.getString("TituloCD"));
                        txtArtista.setText(resultSet.getString("Artista"));
                        txtGenero.setText(resultSet.getString("Genero"));
                        txtDuracion.setText(resultSet.getString("Duracion"));
                        txtCanciones.setText(resultSet.getString("NumeroCanciones"));
                        txtUnidades.setText(resultSet.getString("UnidadesDisponibles"));
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró el CD de audio con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del CD de audio.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

