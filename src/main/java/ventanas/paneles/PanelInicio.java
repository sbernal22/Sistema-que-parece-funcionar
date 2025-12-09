package ventanas.paneles;

import entidades.concretas.Usuario;
import javax.swing.*;
import java.awt.*;

public class PanelInicio extends JPanel {
    public PanelInicio(Usuario usuario) {
        setLayout(new BorderLayout());
        JLabel lblBienvenida = new JLabel("Bienvenido, " + usuario.getNombre(), JLabel.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblBienvenida, BorderLayout.CENTER);
    }
}
