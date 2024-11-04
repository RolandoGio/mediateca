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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActualizarPrestamoForm extends JFrame {
    private JComboBox<String> comboIDPrestamo, comboIDCliente, comboMaterial;
    private JTextField txtFechaPrestamo, txtFechaDevolucion;
    private JButton btnGuardar;
    private String tipoMaterialSeleccionado;
    private String columnaMaterial;

    public ActualizarPrestamoForm() {
        setTitle("Actualizar Préstamo");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 2));

        comboIDPrestamo = new JComboBox<>();
        comboIDCliente = new JComboBox<>();
        comboMaterial = new JComboBox<>();
        txtFechaPrestamo = new JTextField();
        txtFechaDevolucion = new JTextField();
        btnGuardar = new JButton("Guardar");

        add(new JLabel("ID del Préstamo:"));
        add(comboIDPrestamo);
        add(new JLabel("ID Cliente:"));
        add(comboIDCliente);
        add(new JLabel("Material:"));
        add(comboMaterial);
        add(new JLabel("Fecha de Préstamo (YYYY-MM-DD):"));
        add(txtFechaPrestamo);
        add(new JLabel("Fecha de Devolución (YYYY-MM-DD):"));
        add(txtFechaDevolucion);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        comboIDPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosPrestamo((String) comboIDPrestamo.getSelectedItem());
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPrestamo();
            }
        });

        cargarPrestamos();
        cargarClientes();
        setVisible(true);
    }

    private void cargarPrestamos() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT IDPrestamo FROM Prestamos";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    comboIDPrestamo.addItem(resultSet.getString("IDPrestamo"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los préstamos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    private void cargarDatosPrestamo(String idPrestamo) {
        if (idPrestamo == null || idPrestamo.isEmpty()) {
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Prestamos WHERE IDPrestamo = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, idPrestamo);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        comboIDCliente.setSelectedItem(resultSet.getString("IDCliente"));
                        txtFechaPrestamo.setText(resultSet.getString("FechaPrestamo"));
                        txtFechaDevolucion.setText(resultSet.getString("FechaDevolucion"));

                        if (resultSet.getString("LibroID") != null) {
                            tipoMaterialSeleccionado = "Libro";
                            columnaMaterial = "LibroID";
                            cargarMateriales("Libro");
                            comboMaterial.setSelectedItem(resultSet.getString("LibroID"));
                        } else if (resultSet.getString("CDID") != null) {
                            tipoMaterialSeleccionado = "CD de Audio";
                            columnaMaterial = "CDID";
                            cargarMateriales("CD de Audio");
                            comboMaterial.setSelectedItem(resultSet.getString("CDID"));
                        } else if (resultSet.getString("DVDID") != null) {
                            tipoMaterialSeleccionado = "DVD";
                            columnaMaterial = "DVDID";
                            cargarMateriales("DVD");
                            comboMaterial.setSelectedItem(resultSet.getString("DVDID"));
                        } else if (resultSet.getString("RevistaID") != null) {
                            tipoMaterialSeleccionado = "Revista";
                            columnaMaterial = "RevistaID";
                            cargarMateriales("Revista");
                            comboMaterial.setSelectedItem(resultSet.getString("RevistaID"));
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró el préstamo con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void actualizarPrestamo() {
        String idPrestamo = (String) comboIDPrestamo.getSelectedItem();
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

            String sql = "UPDATE Prestamos SET IDCliente = ?, " + columnaMaterial + " = ?, FechaPrestamo = ?, FechaDevolucion = ? WHERE IDPrestamo = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, idCliente);
                statement.setString(2, materialID);
                statement.setString(3, fechaPrestamo);
                statement.setString(4, fechaDevolucion);
                statement.setString(5, idPrestamo);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Préstamo actualizado exitosamente.");
                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
