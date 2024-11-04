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

public class AgregarAutorForm extends JFrame {
    private JTextField txtNombre;
    private JTextField txtNacionalidad;

    public AgregarAutorForm() {
        setTitle("Agregar Autor");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        txtNombre = new JTextField();
        txtNacionalidad = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Nacionalidad:"));
        add(txtNacionalidad);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarAutor();
            }
        });

        setVisible(true);
    }

    private void agregarAutor() {
        String nombre = txtNombre.getText().trim();
        String nacionalidad = txtNacionalidad.getText().trim();

        if (nombre.isEmpty() || nacionalidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Autores (IDAutor, NombreAutor, Nacionalidad) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                String autorID = IdentificadorGenerator.generarIdentificador(connection, "AUT");
                statement.setString(1, autorID);
                statement.setString(2, nombre);
                statement.setString(3, nacionalidad);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Autor agregado exitosamente.");
                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el autor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

