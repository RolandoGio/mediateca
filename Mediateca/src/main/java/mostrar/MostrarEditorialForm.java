package mostrar;



import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarEditorialForm extends JFrame {
    private JTextField txtNombre;
    private JTextField txtPais;

    public MostrarEditorialForm(String id) {
        setTitle("Mostrar Editorial");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        txtNombre = new JTextField();
        txtPais = new JTextField();
        JButton btnCerrar = new JButton("Cerrar");

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("País:"));
        add(txtPais);
        add(new JLabel()); // Espacio vacío
        add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());

        cargarDatosEditorial(id);

        setVisible(true);
    }

    private void cargarDatosEditorial(String id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Editoriales WHERE EditorialID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        txtNombre.setText(resultSet.getString("NombreEditorial"));
                        txtPais.setText(resultSet.getString("PaisEditorial"));
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró la editorial con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de la editorial.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

