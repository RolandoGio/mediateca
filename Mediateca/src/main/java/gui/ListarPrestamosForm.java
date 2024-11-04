package gui;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListarPrestamosForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public ListarPrestamosForm() {
        setTitle("Listar Préstamos");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        cargarPrestamos();

        setVisible(true);
    }

    private void cargarPrestamos() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Prestamos";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                // Limpiar el modelo de tabla
                tableModel.setRowCount(0);
                tableModel.setColumnCount(0);

                // Obtener metadatos de las columnas
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    tableModel.addColumn(resultSet.getMetaData().getColumnName(i));
                }

                // Agregar filas al modelo de tabla
                while (resultSet.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = resultSet.getObject(i);
                    }
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al listar los préstamos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

