package gui;

import database.DatabaseConnection;
import model.IdentificadorGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgregarLibroForm extends JFrame {
    private JTextField txtTitulo, txtAutor, txtEditorial, txtPaginas, txtISBN, txtAnio, txtUnidades;

    public AgregarLibroForm() {
        setTitle("Agregar Libro");
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
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Autor:"));
        add(txtAutor);
        add(new JLabel("Editorial:"));
        add(txtEditorial);
        add(new JLabel("Páginas:"));
        add(txtPaginas);
        add(new JLabel("ISBN (sin guiones):"));
        add(txtISBN);
        add(new JLabel("Año de Publicación (YYYY):"));
        add(txtAnio);
        add(new JLabel("Unidades Disponibles:"));
        add(txtUnidades);
        add(new JLabel());
        add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    try (Connection connection = DatabaseConnection.getConnection()) {
                        agregarLibro(connection, txtTitulo.getText(), txtAutor.getText(), Integer.parseInt(txtPaginas.getText()), txtEditorial.getText(), txtISBN.getText(), Integer.parseInt(txtAnio.getText()), Integer.parseInt(txtUnidades.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al agregar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        setVisible(true);
    }

    private boolean validarCampos() {
        if (txtTitulo.getText().trim().isEmpty() || txtAutor.getText().trim().isEmpty() || txtEditorial.getText().trim().isEmpty() || txtPaginas.getText().trim().isEmpty() || txtISBN.getText().trim().isEmpty() || txtAnio.getText().trim().isEmpty() || txtUnidades.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtISBN.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "El ISBN debe contener solo números sin guiones.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtAnio.getText().matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "El año de publicación debe tener el formato YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean libroExiste(Connection connection, String isbn) throws SQLException {
        String sql = "SELECT 1 FROM Libros WHERE ISBN = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, isbn);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private String obtenerOcrearAutor(Connection connection, String nombre) throws SQLException {
        String autorID = null;
        String sql = "SELECT IDAutor FROM Autores WHERE NombreAutor = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    autorID = resultSet.getString("IDAutor");
                } else {
                    autorID = IdentificadorGenerator.generarIdentificador(connection, "AUT");
                    crearAutor(connection, autorID, nombre, "Nacionalidad del Autor");
                }
            }
        }
        return autorID;
    }

    private void crearAutor(Connection connection, String autorID, String nombre, String nacionalidad) throws SQLException {
        String sql = "INSERT INTO Autores (IDAutor, NombreAutor, Nacionalidad) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, autorID);
            statement.setString(2, nombre);
            statement.setString(3, nacionalidad);
            statement.executeUpdate();
        }
    }

    private String obtenerOcrearEditorial(Connection connection, String nombre) throws SQLException {
        String editorialID = null;
        String sql = "SELECT EditorialID FROM Editoriales WHERE NombreEditorial = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    editorialID = resultSet.getString("EditorialID");
                } else {
                    editorialID = IdentificadorGenerator.generarIdentificador(connection, "EDI");
                    crearEditorial(connection, editorialID, nombre, "País de la Editorial");
                }
            }
        }
        return editorialID;
    }

    private void crearEditorial(Connection connection, String editorialID, String nombre, String pais) throws SQLException {
        String sql = "INSERT INTO Editoriales (EditorialID, NombreEditorial, PaisEditorial) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, editorialID);
            statement.setString(2, nombre);
            statement.setString(3, pais);
            statement.executeUpdate();
        }
    }


    private void agregarLibro(Connection connection, String titulo, String autor, int numPaginas, String editorial, String isbn, int anioPublicacion, int unidadesDisponibles) throws SQLException {
        connection.setAutoCommit(false); // Desactivar el auto-commit

        try {
            if (libroExiste(connection, isbn)) {
                JOptionPane.showMessageDialog(null, "El libro con este ISBN ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String autorID = obtenerOcrearAutor(connection, autor);
                String editorialID = obtenerOcrearEditorial(connection, editorial);

                String sql = "INSERT INTO Libros (LibroID, TituloLibro, IDAutor, EditorialID, Paginas, ISBN, AnioPublicacion, UnidadesDisponibles) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    String libroID = IdentificadorGenerator.generarIdentificador(connection, "LIB");
                    statement.setString(1, libroID);
                    statement.setString(2, titulo);
                    statement.setString(3, autorID);
                    statement.setString(4, editorialID);
                    statement.setInt(5, numPaginas);
                    statement.setString(6, isbn);
                    statement.setInt(7, anioPublicacion);
                    statement.setInt(8, unidadesDisponibles);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Libro agregado exitosamente.");
                }

                connection.commit(); // Confirmar la transacción
            }
        } catch (SQLException ex) {
            connection.rollback(); // Revertir la transacción en caso de error
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            connection.setAutoCommit(true); // Restaurar el auto-commit
        }
    }

}

