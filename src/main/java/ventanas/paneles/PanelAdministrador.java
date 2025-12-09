package ventanas.paneles;

import entidades.concretas.Administrador;
import entidades.concretas.Persona;
import gestores.GestorAdministradores;
import util.PasswordUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelAdministrador extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;
    private GestorAdministradores gestorAdministradores;

    public PanelAdministrador() {
        setLayout(new BorderLayout());
        this.gestorAdministradores = new GestorAdministradores();

        JToolBar toolbar = new JToolBar();
        JButton btnAgregar = new JButton("Agregar");
        toolbar.add(btnAgregar);
        add(toolbar, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[] { "DNI", "Nombre", "Apellido", "Usuario" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());

        actualizarAdministradores();
    }

    private void mostrarDialogoAgregar() {
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtUsuario = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        Object[] message = {
                "Nombre:", txtNombre,
                "Apellido:", txtApellido,
                "DNI:", txtDni,
                "Usuario:", txtUsuario,
                "Contraseña:", txtPassword
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Nuevo Administrador", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                if (txtNombre.getText().length() < 2) {
                    JOptionPane.showMessageDialog(this, "El nombre es demasiado corto.");
                    return;
                }

                if (!txtNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras y espacios.");
                    return;
                }

                if (txtApellido.getText().length() < 2) {
                    JOptionPane.showMessageDialog(this, "El apellido es demasiado corto.");
                    return;
                }

                if (txtPassword.getText().length() < 2) {
                    JOptionPane.showMessageDialog(this, "La contraseña es demasiado corta.");
                    return;
                }

                if (txtUsuario.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Usuario es obligatorio.");
                    return;
                }

                if (!txtApellido.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    JOptionPane.showMessageDialog(this, "El apellido solo puede contener letras y espacios.");
                    return;
                }


                Persona persona = new Persona(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtDni.getText(),
                        "",
                        "");

                Administrador admin = new Administrador(
                        persona,
                        txtUsuario.getText(),
                        PasswordUtil.hashPassword(new String(txtPassword.getPassword())),
                        true);

                gestorAdministradores.agregar(admin);
                actualizarAdministradores();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear administrador: " + ex.getMessage());
            }
        }
    }

    public void actualizarAdministradores() {
        modelo.setRowCount(0);
        List<Administrador> lista = gestorAdministradores.listarTodos();
        for (Administrador a : lista) {
            modelo.addRow(new Object[] {
                    a.getDNI(),
                    a.getNombre(),
                    a.getApellido(),
                    a.getNombreUsuario()
            });
        }
    }
}
