package ventanas;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;

public class VentanaSistema extends JFrame {
    public VentanaSistema() {
        setTitle("Escritorio");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Bienvenido al Sistema", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnIniciar = new JButton("Iniciar Aplicación");
        btnIniciar.addActionListener(e -> {
            VentanaLogin login = new VentanaLogin();
            login.setVisible(true);
        });

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(btnIniciar, BorderLayout.CENTER);

        add(panel);
    }
}
