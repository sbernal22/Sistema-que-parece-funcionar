package ventanas.paneles;

import entidades.concretas.Cliente;
import entidades.concretas.CuentaBancaria;
import entidades.concretas.Tarjeta;
import entidades.concretas.Debito;
import entidades.concretas.Credito;
import entidades.concretas.Usuario;
import gestores.GestorTarjetas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelTarjetas extends JPanel {
    private final Cliente cliente;
    private JTable tabla;
    private DefaultTableModel modelo;
    private GestorTarjetas gestorTarjetas;

    public PanelTarjetas(Usuario usuario) {
        setLayout(new BorderLayout());
        if (!(usuario instanceof Cliente)) {
            this.cliente = null;
            return;
        }
        this.cliente = (Cliente) usuario;
        this.gestorTarjetas = new GestorTarjetas();

        JToolBar toolbar = new JToolBar();
        JButton btnAgregar = new JButton("Agregar");
        toolbar.add(btnAgregar);
        add(toolbar, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[]{"Nro. Tarjeta", "Compañía", "Tipo", "Cuentas afiliadas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> {
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado. No se puede crear tarjeta.");
                return;
            }
            mostrarDialogoAgregar(cliente);
        });

        actualizarTarjetas();
    }

    private void mostrarDialogoAgregar(Cliente cliente) {
        String[] tipos = {"Débito", "Crédito", "Cancelar"};
        int tipoSel = JOptionPane.showOptionDialog(
                this,
                "Seleccione el tipo de tarjeta a crear:",
                "Nueva Tarjeta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]
        );
        if (tipoSel == 2 || tipoSel == JOptionPane.CLOSED_OPTION) return;

        JTextField txtCompania = new JTextField();
        Object[] message = {
                "Compañía:", txtCompania
        };
        int option = JOptionPane.showConfirmDialog(this, message, "Crear Tarjeta", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;

        String compania = txtCompania.getText().trim();
        if (compania.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La compañía no puede estar vacía.");
            return;
        }

        try {
            String numeroGenerado = gestorTarjetas.generarNumeroTarjeta();

            Tarjeta tarjeta;
            if (tipoSel == 0) {
                tarjeta = new Debito(numeroGenerado, compania, 0.0);
            } else {
                tarjeta = new Credito(numeroGenerado, compania);
            }

            gestorTarjetas.agregarTarjeta(tarjeta, cliente.getDNI());
            actualizarTarjetas();
            JOptionPane.showMessageDialog(this, "Tarjeta creada: " + numeroGenerado);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear tarjeta: " + ex.getMessage());
        }
    }

    public void actualizarTarjetas() {
        modelo.setRowCount(0);
        if (cliente == null) return;
        List<Tarjeta> tarjetas = gestorTarjetas.listarTodos(cliente.getDNI());

        for (Tarjeta t : tarjetas) {
            String tipo;
            if (t instanceof Debito) {
                tipo = "Débito";
            } else if (t instanceof Credito) {
                tipo = "Crédito";
            } else {
                tipo = "N/A";
            }
            List<CuentaBancaria> cuentasAfiliadas = t.getCuentasAfiliadas();
            String cuentas;
            if (cuentasAfiliadas.isEmpty()) {
                cuentas = "-";
            } else {
                StringBuilder sb = new StringBuilder();
                for (CuentaBancaria c : cuentasAfiliadas) {
                    sb.append(c.getNumeroCuenta()).append(", ");
                }
                cuentas = sb.substring(0, sb.length() - 2);
            }
            modelo.addRow(new Object[]{
                    t.getNumeroTarjeta(),
                    t.getCompania(),
                    tipo,
                    cuentas
            });
        }
    }
}
