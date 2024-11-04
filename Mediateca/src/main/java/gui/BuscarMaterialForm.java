package gui;

import database.DatabaseConnection;
import mostrar.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuscarMaterialForm extends JFrame {
    private JTextField txtID;
    private JComboBox<String> comboTipo;

    public BuscarMaterialForm() {
        setTitle("Buscar Material");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        txtID = new JTextField();
        comboTipo = new JComboBox<>(new String[]{"Libro", "Revista", "CD de Audio", "DVD", "Usuario", "Autor", "Editorial"});
        JButton btnBuscar = new JButton("Buscar");

        add(new JLabel("ID del Material:"));
        add(txtID);
        add(new JLabel("Tipo de Material:"));
        add(comboTipo);
        add(new JLabel()); // Espacio vacío
        add(btnBuscar);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarMaterial();
            }
        });

        setVisible(true);
    }

    private void buscarMaterial() {
        String id = txtID.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();

        if (id.isEmpty() || tipo == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipoReal = obtenerTipoPorID(id);
        if (tipoReal == null) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún material con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!tipo.equals(tipoReal)) {
            JOptionPane.showMessageDialog(this, "El ID ingresado pertenece a un " + tipoReal + ". Por favor, seleccione el tipo correcto.\nEjemplo de ID para " + tipo + ": " + obtenerEjemploID(tipo), "ID Incorrecto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        switch (tipo) {
            case "Libro":
                new MostrarLibroForm(id);
                break;
            case "Revista":
                new MostrarRevistaForm(id);
                break;
            case "CD de Audio":
                new MostrarCDForm(id);
                break;
            case "DVD":
                new MostrarDVDForm(id);
                break;
            case "Usuario":
                new MostrarUsuarioForm(id);
                break;
            case "Autor":
                new MostrarAutorForm(id);
                break;
            case "Editorial":
                new MostrarEditorialForm(id);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de material no válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerTipoPorID(String id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (existeEnTabla(connection, "Libros", "LibroID", id)) return "Libro";
            if (existeEnTabla(connection, "Revistas", "RevistaID", id)) return "Revista";
            if (existeEnTabla(connection, "CDsAudio", "CDID", id)) return "CD de Audio";
            if (existeEnTabla(connection, "DVDs", "DVDID", id)) return "DVD";
            if (existeEnTabla(connection, "Clientes", "IDCliente", id)) return "Usuario";
            if (existeEnTabla(connection, "Autores", "IDAutor", id)) return "Autor";
            if (existeEnTabla(connection, "Editoriales", "EditorialID", id)) return "Editorial";
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private boolean existeEnTabla(Connection connection, String tabla, String columna, String id) throws SQLException {
        String sql = "SELECT 1 FROM " + tabla + " WHERE " + columna + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private String obtenerEjemploID(String tipo) {
        switch (tipo) {
            case "Libro":
                return "LIB00001";
            case "Revista":
                return "REV00001";
            case "CD de Audio":
                return "CDA00001";
            case "DVD":
                return "DVD00001";
            case "Usuario":
                return "CLI00001";
            case "Autor":
                return "AUT00001";
            case "Editorial":
                return "EDI00001";
            default:
                return "";
        }
    }
}
