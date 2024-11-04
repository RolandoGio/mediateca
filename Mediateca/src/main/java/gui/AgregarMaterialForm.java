package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarMaterialForm extends JFrame {
    public AgregarMaterialForm() {
        setTitle("Agregar Material");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar el layout
        setLayout(new GridLayout(5, 1));

        // Crear botones
        JButton btnAgregarLibro = new JButton("Agregar Libro");
        JButton btnAgregarRevista = new JButton("Agregar Revista");
        JButton btnAgregarCD = new JButton("Agregar CD de Audio");
        JButton btnAgregarDVD = new JButton("Agregar DVD");

        // Agregar botones al frame
        add(btnAgregarLibro);
        add(btnAgregarRevista);
        add(btnAgregarCD);
        add(btnAgregarDVD);

        // Acción para el botón Agregar Libro
        btnAgregarLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarLibroForm();
            }
        });

        // Acción para el botón Agregar Revista
        btnAgregarRevista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarRevistaForm();
            }
        });

        // Acción para el botón Agregar CD de Audio
        btnAgregarCD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarCDForm();
            }
        });

        // Acción para el botón Agregar DVD
        btnAgregarDVD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarDVDForm();
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }
}






