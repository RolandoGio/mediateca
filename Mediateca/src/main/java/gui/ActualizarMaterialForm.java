package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActualizarMaterialForm extends JFrame {
    public ActualizarMaterialForm() {
        setTitle("Actualizar Material");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1));

        // Crear botones para cada tipo de material
        JButton btnActualizarLibro = new JButton("Actualizar Libro");
        JButton btnActualizarRevista = new JButton("Actualizar Revista");
        JButton btnActualizarCD = new JButton("Actualizar CD de Audio");
        JButton btnActualizarDVD = new JButton("Actualizar DVD");
        JButton btnActualizarUsuario = new JButton("Actualizar Usuario");
        JButton btnActualizarAutor = new JButton("Actualizar Autor");
        JButton btnActualizarEditorial = new JButton("Actualizar Editorial");

        // Agregar botones al frame
        add(btnActualizarLibro);
        add(btnActualizarRevista);
        add(btnActualizarCD);
        add(btnActualizarDVD);
        add(btnActualizarUsuario);
        add(btnActualizarAutor);
        add(btnActualizarEditorial);

        // Acción para cada botón
        btnActualizarLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new actualizar.ActualizarLibroForm();
            }
        });

        btnActualizarRevista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new actualizar.ActualizarRevistaForm();
            }
        });

        btnActualizarCD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new actualizar.ActualizarCDForm();
            }
        });

        btnActualizarDVD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new actualizar.ActualizarDVDForm();
            }
        });

        btnActualizarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new actualizar.ActualizarUsuarioForm();
            }
        });

        btnActualizarAutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new actualizar.ActualizarAutorForm();
            }
        });

        btnActualizarEditorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new actualizar.ActualizarEditorialForm();
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }
}
