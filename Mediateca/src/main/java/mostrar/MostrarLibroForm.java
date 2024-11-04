package mostrar;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarLibroForm extends JFrame {
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtEditorial;
    private JTextField txtPaginas;
    private JTextField txtISBN;
    private JTextField txtAnio;
    private JTextField txtUnidades;

    public MostrarLibroForm(String id) {
        setTitle("Mostrar Libro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtEditorial = new JTextField();
        txtPaginas = new JTextField();
        txtISBN = new JTextField();
        txtAnio = new JTextField();
        txtUnidades = new JTextField();
        JButton btnCerrar = new JButton("Cerrar");

        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Autor:"));
        add(txtAutor);
        add(new JLabel("Editorial:"));
        add(txtEditorial);
        add(new JLabel("Páginas:"));
        add(txtPaginas);
        add(new JLabel("ISBN:"));
        add(txtISBN);
        add(new JLabel("Año de Publicación:"));
        add(txtAnio);
        add(new JLabel("Unidades Disponibles:"));
        add(txtUnidades);
        add(new JLabel()); // Espacio vacío
        add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());

        cargarDatosLibro(id);

        setVisible(true);
    }

    private void cargarDatosLibro(String id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Libros WHERE LibroID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        txtTitulo.setText(resultSet.getString("TituloLibro"));
                        txtAutor.setText(resultSet.getString("IDAutor"));
                        txtEditorial.setText(resultSet.getString("EditorialID"));
                        txtPaginas.setText(resultSet.getString("Paginas"));
                        txtISBN.setText(resultSet.getString("ISBN"));
                        txtAnio.setText(resultSet.getString("AnioPublicacion"));
                        txtUnidades.setText(resultSet.getString("UnidadesDisponibles"));
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró el libro con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del libro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

