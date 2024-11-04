package gui;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrarMaterialForm extends JFrame {
    private JTextField txtID;
    private JComboBox<String> comboTipo;

    public BorrarMaterialForm() {
        setTitle("Borrar Material");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        txtID = new JTextField();
        comboTipo = new JComboBox<>(new String[]{"Libro", "Revista", "CD de Audio", "DVD", "Usuario", "Autor", "Editorial"});
        JButton btnBorrar = new JButton("Borrar");

        add(new JLabel("ID del Material:"));
        add(txtID);
        add(new JLabel("Tipo de Material:"));
        add(comboTipo);
        add(new JLabel()); // Espacio vacío
        add(btnBorrar);

        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarMaterial();
            }
        });

        setVisible(true);
    }

    private void borrarMaterial() {
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

        String tabla = "";
        String idColumna = "";

        switch (tipo) {
            case "Libro":
                tabla = "Libros";
                idColumna = "LibroID";
                break;
            case "Revista":
                tabla = "Revistas";
                idColumna = "RevistaID";
                break;
            case "CD de Audio":
                tabla = "CDsAudio";
                idColumna = "CDID";
                break;
            case "DVD":
                tabla = "DVDs";
                idColumna = "DVDID";
                break;
            case "Usuario":
                tabla = "Clientes";
                idColumna = "IDCliente";
                break;
            case "Autor":
                tabla = "Autores";
                idColumna = "IDAutor";
                break;
            case "Editorial":
                tabla = "Editoriales";
                idColumna = "EditorialID";
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de material no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM " + tabla + " WHERE " + idColumna + " = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Material borrado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el material con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al borrar el material.", "Error", JOptionPane.ERROR_MESSAGE);
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
