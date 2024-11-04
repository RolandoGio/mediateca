package gui;

import actualizar.*;
import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MediatecaGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public MediatecaGUI() {
        setTitle("Mediateca");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JPanel panelBotones = new JPanel(new FlowLayout());

        // Botones de ejemplo
        JButton btnAgregar = new JButton("Agregar Material");
        JButton btnActualizarMaterial = new JButton("Actualizar Material");
        JButton btnListar = new JButton("Listar Material");
        JButton btnBorrar = new JButton("Borrar Material");
        JButton btnBuscar = new JButton("Buscar Material");

        // Agregar botones al panel de botones
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizarMaterial);
        panelBotones.add(btnListar);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnBuscar);

        panelPrincipal.add(panelBotones, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);

        // Acción para el botón Agregar
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMenuAgregar(e);
            }
        });

        // Acción para el botón Actualizar Material
        btnActualizarMaterial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMenuActualizar(e);
            }
        });

        // Acción para el botón Listar
        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMenuListar(e);
            }
        });

        // Acción para el botón Borrar
        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMenuBorrar(e);
            }
        });

        // Acción para el botón Buscar
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMenuBuscar(e);
            }
        });

        setVisible(true);
    }

    private void mostrarMenuAgregar(ActionEvent e) {
        JPopupMenu menuAgregar = new JPopupMenu();
        JMenuItem itemAgregarLibro = new JMenuItem("Agregar Libro");
        JMenuItem itemAgregarRevista = new JMenuItem("Agregar Revista");
        JMenuItem itemAgregarCD = new JMenuItem("Agregar CD de Audio");
        JMenuItem itemAgregarDVD = new JMenuItem("Agregar DVD");
        JMenuItem itemAgregarUsuario = new JMenuItem("Agregar Usuario");
        JMenuItem itemAgregarAutor = new JMenuItem("Agregar Autor");
        JMenuItem itemAgregarEditorial = new JMenuItem("Agregar Editorial");
        JMenuItem itemAgregarPrestamo = new JMenuItem("Agregar Préstamo");

        itemAgregarLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarLibroForm();
            }
        });

        itemAgregarRevista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarRevistaForm();
            }
        });

        itemAgregarCD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarCDForm();
            }
        });

        itemAgregarDVD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarDVDForm();
            }
        });

        itemAgregarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarUsuarioForm();
            }
        });

        itemAgregarAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarAutorForm();
            }
        });

        itemAgregarEditorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarEditorialForm();
            }
        });

        itemAgregarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarPrestamoForm();
            }
        });

        menuAgregar.add(itemAgregarLibro);
        menuAgregar.add(itemAgregarRevista);
        menuAgregar.add(itemAgregarCD);
        menuAgregar.add(itemAgregarDVD);
        menuAgregar.add(itemAgregarUsuario);
        menuAgregar.add(itemAgregarAutor);
        menuAgregar.add(itemAgregarEditorial);
        menuAgregar.add(itemAgregarPrestamo);

        Component source = (Component) e.getSource();
        menuAgregar.show(source, 0, source.getHeight());
    }

    private void mostrarMenuActualizar(ActionEvent e) {
        JPopupMenu menuActualizar = new JPopupMenu();
        JMenuItem itemActualizarLibro = new JMenuItem("Actualizar Libro");
        JMenuItem itemActualizarRevista = new JMenuItem("Actualizar Revista");
        JMenuItem itemActualizarCD = new JMenuItem("Actualizar CD de Audio");
        JMenuItem itemActualizarDVD = new JMenuItem("Actualizar DVD");
        JMenuItem itemActualizarUsuario = new JMenuItem("Actualizar Usuario");
        JMenuItem itemActualizarAutor = new JMenuItem("Actualizar Autor");
        JMenuItem itemActualizarEditorial = new JMenuItem("Actualizar Editorial");
        JMenuItem itemActualizarPrestamo = new JMenuItem("Actualizar Préstamo");

        itemActualizarLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActualizarLibroForm();
            }
        });

        itemActualizarRevista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActualizarRevistaForm();
            }
        });

        itemActualizarCD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActualizarCDForm();
            }
        });

        itemActualizarDVD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActualizarDVDForm();
            }
        });

        itemActualizarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActualizarUsuarioForm();
            }
        });

        itemActualizarAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActualizarAutorForm();
            }
        });

        itemActualizarEditorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActualizarEditorialForm();
            }
        });

        itemActualizarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActualizarPrestamoForm();
            }
        });

        menuActualizar.add(itemActualizarLibro);
        menuActualizar.add(itemActualizarRevista);
        menuActualizar.add(itemActualizarCD);
        menuActualizar.add(itemActualizarDVD);
        menuActualizar.add(itemActualizarUsuario);
        menuActualizar.add(itemActualizarAutor);
        menuActualizar.add(itemActualizarEditorial);
        menuActualizar.add(itemActualizarPrestamo);

        Component source = (Component) e.getSource();
        menuActualizar.show(source, 0, source.getHeight());
    }

    private void mostrarMenuListar(ActionEvent e) {
        JPopupMenu menuListar = new JPopupMenu();
        JMenuItem itemListarLibros = new JMenuItem("Listar Libros");
        JMenuItem itemListarRevistas = new JMenuItem("Listar Revistas");
        JMenuItem itemListarCDs = new JMenuItem("Listar CDs de Audio");
        JMenuItem itemListarDVDs = new JMenuItem("Listar DVDs");
        JMenuItem itemListarUsuarios = new JMenuItem("Listar Usuarios");
        JMenuItem itemListarAutores = new JMenuItem("Listar Autores");
        JMenuItem itemListarEditoriales = new JMenuItem("Listar Editoriales");
        JMenuItem itemListarPrestamos = new JMenuItem("Listar Préstamos");

        itemListarLibros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarMaterial("Libros");
            }
        });

        itemListarRevistas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarMaterial("Revistas");
            }
        });

        itemListarCDs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarMaterial("CDsAudio");
            }
        });

        itemListarDVDs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarMaterial("DVDs");
            }
        });

        itemListarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarMaterial("Clientes");
            }
        });

        itemListarAutores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarMaterial("Autores");
            }
        });

        itemListarEditoriales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarMaterial("Editoriales");
            }
        });

        itemListarPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarMaterial("Prestamos");
            }
        });

        menuListar.add(itemListarLibros);
        menuListar.add(itemListarRevistas);
        menuListar.add(itemListarCDs);
        menuListar.add(itemListarDVDs);
        menuListar.add(itemListarUsuarios);
        menuListar.add(itemListarAutores);
        menuListar.add(itemListarEditoriales);
        menuListar.add(itemListarPrestamos);

        Component source = (Component) e.getSource();
        menuListar.show(source, 0, source.getHeight());
    }

    private void mostrarMenuBorrar(ActionEvent e) {
        JPopupMenu menuBorrar = new JPopupMenu();
        JMenuItem itemBorrarLibro = new JMenuItem("Borrar Libro");
        JMenuItem itemBorrarRevista = new JMenuItem("Borrar Revista");
        JMenuItem itemBorrarCD = new JMenuItem("Borrar CD de Audio");
        JMenuItem itemBorrarDVD = new JMenuItem("Borrar DVD");
        JMenuItem itemBorrarUsuario = new JMenuItem("Borrar Usuario");
        JMenuItem itemBorrarAutor = new JMenuItem("Borrar Autor");
        JMenuItem itemBorrarEditorial = new JMenuItem("Borrar Editorial");
        JMenuItem itemBorrarPrestamo = new JMenuItem("Borrar Préstamo");

        itemBorrarLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BorrarMaterialForm();
            }
        });

        itemBorrarRevista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BorrarMaterialForm();
            }
        });

        itemBorrarCD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BorrarMaterialForm();
            }
        });

        itemBorrarDVD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BorrarMaterialForm();
            }
        });

        itemBorrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BorrarMaterialForm();
            }
        });

        itemBorrarAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BorrarMaterialForm();
            }
        });

        itemBorrarEditorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BorrarMaterialForm();
            }
        });

        itemBorrarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BorrarPrestamoForm();
            }
        });

        menuBorrar.add(itemBorrarLibro);
        menuBorrar.add(itemBorrarRevista);
        menuBorrar.add(itemBorrarCD);
        menuBorrar.add(itemBorrarDVD);
        menuBorrar.add(itemBorrarUsuario);
        menuBorrar.add(itemBorrarAutor);
        menuBorrar.add(itemBorrarEditorial);
        menuBorrar.add(itemBorrarPrestamo);

        Component source = (Component) e.getSource();
        menuBorrar.show(source, 0, source.getHeight());
    }

    private void mostrarMenuBuscar(ActionEvent e) {
        JPopupMenu menuBuscar = new JPopupMenu();
        JMenuItem itemBuscarLibro = new JMenuItem("Buscar Libro");
        JMenuItem itemBuscarRevista = new JMenuItem("Buscar Revista");
        JMenuItem itemBuscarCD = new JMenuItem("Buscar CD de Audio");
        JMenuItem itemBuscarDVD = new JMenuItem("Buscar DVD");
        JMenuItem itemBuscarUsuario = new JMenuItem("Buscar Usuario");
        JMenuItem itemBuscarAutor = new JMenuItem("Buscar Autor");
        JMenuItem itemBuscarEditorial = new JMenuItem("Buscar Editorial");
        JMenuItem itemBuscarPrestamo = new JMenuItem("Buscar Préstamo");

        itemBuscarLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarMaterialForm();
            }
        });

        itemBuscarRevista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarMaterialForm();
            }
        });

        itemBuscarCD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarMaterialForm();
            }
        });

        itemBuscarDVD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarMaterialForm();
            }
        });

        itemBuscarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarMaterialForm();
            }
        });

        itemBuscarAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarMaterialForm();
            }
        });

        itemBuscarEditorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarMaterialForm();
            }
        });

        itemBuscarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscarPrestamoForm();
            }
        });

        menuBuscar.add(itemBuscarLibro);
        menuBuscar.add(itemBuscarRevista);
        menuBuscar.add(itemBuscarCD);
        menuBuscar.add(itemBuscarDVD);
        menuBuscar.add(itemBuscarUsuario);
        menuBuscar.add(itemBuscarAutor);
        menuBuscar.add(itemBuscarEditorial);
        menuBuscar.add(itemBuscarPrestamo);

        Component source = (Component) e.getSource();
        menuBuscar.show(source, 0, source.getHeight());
    }

    private void listarMaterial(String tabla) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM " + tabla;
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
            JOptionPane.showMessageDialog(this, "Error al listar el material.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MediatecaGUI());
    }
}
