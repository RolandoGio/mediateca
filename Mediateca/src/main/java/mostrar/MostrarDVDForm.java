package mostrar;



import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarDVDForm extends JFrame {
    private JTextField txtTitulo;
    private JTextField txtDirector;
    private JTextField txtGenero;
    private JTextField txtDuracion;
    private JTextField txtUnidades;

    public MostrarDVDForm(String id) {
        setTitle("Mostrar DVD");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        txtTitulo = new JTextField();
        txtDirector = new JTextField();
        txtGenero = new JTextField();
        txtDuracion = new JTextField();
        txtUnidades = new JTextField();
        JButton btnCerrar = new JButton("Cerrar");

        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Director:"));
        add(txtDirector);
        add(new JLabel("Género:"));
        add(txtGenero);
        add(new JLabel("Duración (min):"));
        add(txtDuracion);
        add(new JLabel("Unidades Disponibles:"));
        add(txtUnidades);
        add(new JLabel()); // Espacio vacío
        add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());

        cargarDatosDVD(id);

        setVisible(true);
    }

    private void cargarDatosDVD(String id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM DVDs WHERE DVDID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        txtTitulo.setText(resultSet.getString("TituloDVD"));
                        txtDirector.setText(resultSet.getString("Director"));
                        txtGenero.setText(resultSet.getString("Genero"));
                        txtDuracion.setText(resultSet.getString("Duracion"));
                        txtUnidades.setText(resultSet.getString("UnidadesDisponibles"));
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró el DVD con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del DVD.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
