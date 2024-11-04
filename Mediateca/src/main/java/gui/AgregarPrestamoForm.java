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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarPrestamoForm extends JFrame {
    private JComboBox<String> comboIDCliente, comboTipoMaterial, comboMaterial;
    private JTextField txtFechaPrestamo, txtFechaDevolucion;
    private JButton btnGuardar;
    private String tipoMaterialSeleccionado;

    public AgregarPrestamoForm() {
        setTitle("Agregar Préstamo");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 2));

        comboIDCliente = new JComboBox<>();
        comboTipoMaterial = new JComboBox<>(new String[]{"Libro", "CD de Audio", "DVD", "Revista"});
        comboMaterial = new JComboBox<>();
        txtFechaPrestamo = new JTextField();
        txtFechaDevolucion = new JTextField();
        btnGuardar = new JButton("Guardar");

        // Establecer la fecha actual en el campo de fecha de préstamo
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        txtFechaPrestamo.setText(currentDate);

        add(new JLabel("ID Cliente:"));
        add(comboIDCliente);
        add(new JLabel("Tipo de Material:"));
        add(comboTipoMaterial);
        add(new JLabel("Material:"));
        add(comboMaterial);
        add(new JLabel("Fecha de Préstamo (YYYY-MM-DD):"));
        add(txtFechaPrestamo);
        add(new JLabel("Fecha de Devolución (YYYY-MM-DD):"));
        add(txtFechaDevolucion);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        comboTipoMaterial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipoMaterialSeleccionado = (String) comboTipoMaterial.getSelectedItem();
                cargarMateriales(tipoMaterialSeleccionado);
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPrestamo();
            }
        });

        cargarClientes();
        setVisible(true);
    }

    private void cargarClientes() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT IDCliente FROM Clientes";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    comboIDCliente.addItem(resultSet.getString("IDCliente"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los clientes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarMateriales(String tipo) {
        comboMaterial.removeAllItems();
        String tabla = "";
        String columna = "";

        switch (tipo) {
            case "Libro":
                tabla = "Libros";
                columna = "LibroID";
                break;
            case "CD de Audio":
                tabla = "CDsAudio";
                columna = "CDID";
                break;
            case "DVD":
                tabla = "DVDs";
                columna = "DVDID";
                break;
            case "Revista":
                tabla = "Revistas";
                columna = "RevistaID";
                break;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT " + columna + " FROM " + tabla;
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    comboMaterial.addItem(resultSet.getString(columna));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los materiales.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean verificarDisponibilidad(Connection connection, String tipo, String id) throws SQLException {
        String tabla = "";
        String columna = "";

        switch (tipo) {
            case "Libro":
                tabla = "Libros";
                columna = "LibroID";
                break;
            case "CD de Audio":
                tabla = "CDsAudio";
                columna = "CDID";
                break;
            case "DVD":
                tabla = "DVDs";
                columna = "DVDID";
                break;
            case "Revista":
                tabla = "Revistas";
                columna = "RevistaID";
                break;
        }

        if (id == null || id.isEmpty()) {
            return true; // No hay material seleccionado, no es necesario verificar
        }

        String sql = "SELECT UnidadesDisponibles, " +
                "(SELECT COUNT(*) FROM Prestamos WHERE " + columna + " = ? AND FechaDevolucion > GETDATE()) AS PrestamosActivos " +
                "FROM " + tabla + " WHERE " + columna + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.setString(2, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int unidadesDisponibles = resultSet.getInt("UnidadesDisponibles");
                    int prestamosActivos = resultSet.getInt("PrestamosActivos");
                    return unidadesDisponibles > prestamosActivos;
                }
            }
        }
        return false;
    }

    private void agregarPrestamo() {
        String idCliente = (String) comboIDCliente.getSelectedItem();
        String materialID = (String) comboMaterial.getSelectedItem();
        String fechaPrestamo = txtFechaPrestamo.getText().trim();
        String fechaDevolucion = txtFechaDevolucion.getText().trim();

        if (idCliente.isEmpty() || fechaPrestamo.isEmpty() || fechaDevolucion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos ID Cliente, Fecha de Préstamo y Fecha de Devolución son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que la fecha de devolución no sea anterior a la fecha de préstamo
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date prestamoDate = dateFormat.parse(fechaPrestamo);
            Date devolucionDate = dateFormat.parse(fechaDevolucion);
            if (devolucionDate.before(prestamoDate)) {
                JOptionPane.showMessageDialog(this, "La fecha de devolución no puede ser anterior a la fecha de préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            boolean disponibilidad = verificarDisponibilidad(connection, tipoMaterialSeleccionado, materialID);
            if (!disponibilidad) {
                JOptionPane.showMessageDialog(this, "No hay unidades disponibles del material seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String columnaMaterial = tipoMaterialSeleccionado.equals("CD de Audio") ? "CDID" : tipoMaterialSeleccionado + "ID";
            String sql = "INSERT INTO Prestamos (IDPrestamo, IDCliente, " + columnaMaterial + ", FechaPrestamo, FechaDevolucion) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                String prestamoID = IdentificadorGenerator.generarIdentificador(connection, "PRE");
                statement.setString(1, prestamoID);
                statement.setString(2, idCliente);
                statement.setString(3, materialID);
                statement.setString(4, fechaPrestamo);
                statement.setString(5, fechaDevolucion);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Préstamo agregado exitosamente.");
                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}



