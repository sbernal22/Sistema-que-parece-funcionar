package main;

import javax.swing.SwingUtilities;
import sistema.*;
import ventanas.VentanaSistema;

public class Main {
    public static void main(String[] args) {
        SistemaBanco.getInstance();
        SwingUtilities.invokeLater(() -> new VentanaSistema().setVisible(true));
    }
}