package mostrar;


import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarRevistaForm extends JFrame {
    private JTextField txtTitulo;
    private JTextField txtEditorial;
    private JTextField txtPeriodicidad;
    private JTextField txtFecha;
    private JTextField txtUnidades;

    public MostrarRevistaForm(String id) {
        setTitle("Mostrar Revista");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        txtTitulo = new JTextField();
        txtEditorial = new JTextField();
        txtPeriodicidad = new JTextField();
        txtFecha = new JTextField();
        txtUnidades = new JTextField();
        JButton btnCerrar = new JButton("Cerrar");

        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Editorial:"));
        add(txtEditorial);
        add(new JLabel("Periodicidad:"));
        add(txtPeriodicidad);
        add(new JLabel("Fecha de Publicación:"));
        add(txtFecha);
        add(new JLabel("Unidades Disponibles:"));
        add(txtUnidades);
        add(new JLabel()); // Espacio vacío
        add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());

        cargarDatosRevista(id);

        setVisible(true);
    }

    private void cargarDatosRevista(String id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Revistas WHERE RevistaID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        txtTitulo.setText(resultSet.getString("TituloRevista"));
                        txtEditorial.setText(resultSet.getString("EditorialID"));
                        txtPeriodicidad.setText(resultSet.getString("Periodicidad"));
                        txtFecha.setText(resultSet.getString("FechaPublicacion"));
                        txtUnidades.setText(resultSet.getString("UnidadesDisponibles"));
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró la revista con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de la revista.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

