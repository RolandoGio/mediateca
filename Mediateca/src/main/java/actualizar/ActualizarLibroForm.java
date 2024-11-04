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

public class ActualizarLibroForm extends JFrame {
    private JComboBox<String> comboLibros;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtEditorial;
    private JTextField txtPaginas;
    private JTextField txtISBN;
    private JTextField txtAnio;
    private JTextField txtUnidades;

    public ActualizarLibroForm() {
        setTitle("Actualizar Libro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 2));

        // Crear componentes del formulario
        JLabel lblSeleccionarLibro = new JLabel("Seleccionar Libro:");
        comboLibros = new JComboBox<>();
        cargarLibros();

        JLabel lblTitulo = new JLabel("Título:");
        txtTitulo = new JTextField();
        JLabel lblAutor = new JLabel("Autor:");
        txtAutor = new JTextField();
        JLabel lblEditorial = new JLabel("Editorial:");
        txtEditorial = new JTextField();
        JLabel lblPaginas = new JLabel("Páginas:");
        txtPaginas = new JTextField();
        JLabel lblISBN = new JLabel("ISBN:");
        txtISBN = new JTextField();
        JLabel lblAnio = new JLabel("Año:");
        txtAnio = new JTextField();
        JLabel lblUnidades = new JLabel("Unidades:");
        txtUnidades = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        // Agregar componentes al frame
        add(lblSeleccionarLibro);
        add(comboLibros);
        add(lblTitulo);
        add(txtTitulo);
        add(lblAutor);
        add(txtAutor);
        add(lblEditorial);
        add(txtEditorial);
        add(lblPaginas);
        add(txtPaginas);
        add(lblISBN);
        add(txtISBN);
        add(lblAnio);
        add(txtAnio);
        add(lblUnidades);
        add(txtUnidades);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        // Acción para el botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarLibro();
            }
        });

        // Acción para seleccionar un libro
        comboLibros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosLibro();
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }

    private void cargarLibros() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT LibroID, TituloLibro FROM Libros";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String libroID = resultSet.getString("LibroID");
                    String tituloLibro = resultSet.getString("TituloLibro");
                    comboLibros.addItem(libroID + " - " + tituloLibro);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los libros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosLibro() {
        String seleccionado = (String) comboLibros.getSelectedItem();
        if (seleccionado != null) {
            String libroID = seleccionado.split(" - ")[0];
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM Libros WHERE LibroID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, libroID);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            txtTitulo.setText(resultSet.getString("TituloLibro"));
                            txtAutor.setText(resultSet.getString("IDAutor"));
                            txtEditorial.setText(resultSet.getString("EditorialID"));
                            txtPaginas.setText(resultSet.getString("Paginas"));
                            txtISBN.setText(resultSet.getString("ISBN"));
                            txtAnio.setText(resultSet.getString("AnioPublicacion"));
                            txtUnidades.setText(resultSet.getString("UnidadesDisponibles"));
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar los datos del libro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarLibro() {
        String seleccionado = (String) comboLibros.getSelectedItem();
        if (seleccionado != null) {
            String libroID = seleccionado.split(" - ")[0];
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "UPDATE Libros SET TituloLibro = ?, IDAutor = ?, EditorialID = ?, Paginas = ?, ISBN = ?, AnioPublicacion = ?, UnidadesDisponibles = ? WHERE LibroID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, txtTitulo.getText());
                    statement.setString(2, txtAutor.getText());
                    statement.setString(3, txtEditorial.getText());
                    statement.setInt(4, Integer.parseInt(txtPaginas.getText()));
                    statement.setString(5, txtISBN.getText());
                    statement.setInt(6, Integer.parseInt(txtAnio.getText()));
                    statement.setInt(7, Integer.parseInt(txtUnidades.getText()));
                    statement.setString(8, libroID);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Libro actualizado exitosamente.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al actualizar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

