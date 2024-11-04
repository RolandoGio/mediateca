package gui;

import database.DatabaseConnection;
import model.IdentificadorGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AgregarEditorialForm extends JFrame {
    private JTextField txtNombre;
    private JTextField txtPais;

    public AgregarEditorialForm() {
        setTitle("Agregar Editorial");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        txtNombre = new JTextField();
        txtPais = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("País:"));
        add(txtPais);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEditorial();
            }
        });

        setVisible(true);
    }

    private void agregarEditorial() {
        String nombre = txtNombre.getText().trim();
        String pais = txtPais.getText().trim();

        if (nombre.isEmpty() || pais.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Editoriales (EditorialID, NombreEditorial, PaisEditorial) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                String editorialID = IdentificadorGenerator.generarIdentificador(connection, "EDI");
                statement.setString(1, editorialID);
                statement.setString(2, nombre);
                statement.setString(3, pais);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Editorial agregada exitosamente.");
                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar la editorial.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

