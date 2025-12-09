package ventanas.paneles;

import entidades.concretas.Cajero;
import gestores.GestorCajeros;
import sistema.SistemaBanco;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelCajeros extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtId;
    private JCheckBox chkDisponible;
    private GestorCajeros gestorCajeros;

    public PanelCajeros() {
        setLayout(new BorderLayout());
        this.gestorCajeros = SistemaBanco.getInstance().getGestorCajeros();

        modelo = new DefaultTableModel(new Object[] { "ID", "Disponible" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelAgregar = new JPanel(new FlowLayout());
        txtId = new JTextField(10);
        chkDisponible = new JCheckBox("Disponible");
        chkDisponible.setSelected(true);
        JButton btnAgregar = new JButton("Agregar");

        panelAgregar.add(new JLabel("ID Cajero:"));
        panelAgregar.add(txtId);
        panelAgregar.add(chkDisponible);
        panelAgregar.add(btnAgregar);

        add(panelAgregar, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarCajero());

        actualizarCajeros();
    }

    private void agregarCajero() {
        try {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El ID no puede estar vacío");
                return;
            }

            Cajero cajero = new Cajero(id, chkDisponible.isSelected());
            gestorCajeros.agregarCajero(cajero);
            actualizarCajeros();

            txtId.setText("");
            chkDisponible.setSelected(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear cajero: " + ex.getMessage());
        }
    }

    public void actualizarCajeros() {
        modelo.setRowCount(0);
        List<Cajero> lista = gestorCajeros.listarTodos();
        for (Cajero c : lista) {
            modelo.addRow(new Object[] {
                    c.getId(),
                    c.getDisponible() ? "Si" : "No"
            });
        }
    }
}
