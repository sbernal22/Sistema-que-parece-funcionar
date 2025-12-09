package ventanas.paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import entidades.concretas.Administrador;
import entidades.concretas.Cliente;
import entidades.concretas.Empleado;
import entidades.concretas.Persona;
import entidades.concretas.Usuario;
import sistema.SistemaBanco;
import gestores.GestorUsuarios;

public class PanelUsuarios extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;
    private GestorUsuarios gestorUsuarios;

    public PanelUsuarios() {
        setLayout(new BorderLayout());
        this.gestorUsuarios = SistemaBanco.getInstance().getGestorUsuarios();

        JToolBar toolbar = new JToolBar();
        JButton btnAgregar = new JButton("Crear");
        toolbar.add(btnAgregar);
        add(toolbar, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[] {
                "Usuario", "Nombre", "Apellido", "DNI", "Rol", "Estado"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());

        recargarTablaUsuarios();
    }

    private void mostrarDialogoAgregar() {
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtUsuario = new JTextField();
        JPasswordField txtContrasenia = new JPasswordField();
        JComboBox<String> cmbRol = new JComboBox<>(new String[] {"Cliente", "Empleado"});

        Object[] message = {
                "Nombre:", txtNombre,
                "Apellido:", txtApellido,
                "DNI:", txtDni,
                "Teléfono:", txtTelefono,
                "Dirección:", txtDireccion,
                "Nombre de usuario:", txtUsuario,
                "Contraseña:", txtContrasenia,
                "Rol (para crear subclase):", cmbRol
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Crear Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String dni = txtDni.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String usuarioTxt = txtUsuario.getText().trim();
                String contrasenia = new String(txtContrasenia.getPassword()).trim();
                String rol = (String) cmbRol.getSelectedItem();
                if (nombre.length() < 2) {
                    JOptionPane.showMessageDialog(this, "El nombre es demasiado corto.");
                    return;
                }
                if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras y espacios.");
                    return;
                }
                if (apellido.length() < 2) {
                    JOptionPane.showMessageDialog(this, "El apellido es demasiado corto.");
                    return;
                }
                if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    JOptionPane.showMessageDialog(this, "El apellido solo puede contener letras y espacios.");
                    return;
                }
                if (contrasenia.length() < 2) {
                    JOptionPane.showMessageDialog(this, "La contraseña es demasiado corta.");
                    return;
                }
                if (!dni.matches("\\d{8}")) {
                    JOptionPane.showMessageDialog(this, "El DNI debe contener exactamente 8 dígitos numéricos.");
                    return;
                }
                if(!telefono.isEmpty()){
                    if (!telefono.matches("\\d{9}")) {
                        JOptionPane.showMessageDialog(this, "El telefono debe contener exactamente 9 dígitos numéricos.");
                        return;
                    }
                }
                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || usuarioTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nombre, Apellido, DNI y Usuario son obligatorios.");
                    return;
                }

                if (gestorUsuarios.buscarPorUsuario(usuarioTxt) != null) {
                    JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese nombre de usuario.");
                    return;
                }

                Persona persona = new Persona(nombre, apellido, dni, telefono, direccion);
                
                if (rol.equals("Cliente")) {
                    Cliente nuevo = new Cliente(persona, usuarioTxt, contrasenia, true);
                    SistemaBanco.getInstance().getGestorClientes().agregarCliente(nuevo);
                } else {
                    Empleado nuevo = new Empleado(persona, usuarioTxt, contrasenia, true);
                    SistemaBanco.getInstance().getGestorEmpleados().agregarEmpleado(nuevo);
                }

                recargarTablaUsuarios();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear usuario: " + ex.getMessage());
            }
        }
    }

    private void recargarTablaUsuarios() {
        modelo.setRowCount(0);
        List<Usuario> lista = gestorUsuarios.listarTodos();
        for (Usuario u : lista) {
            String rol = deducirRol(u);
            String estado = u.getEstado() ? "Activo" : "Inactivo";
            modelo.addRow(new Object[] {
                    u.getNombreUsuario(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getDNI(),
                    rol,
                    estado
            });
        }
    }

    private String deducirRol(Usuario u) {
        String rol = null;
        try {
            if (u.getTipoRol() != null) {
                rol = u.getTipoRol().toString();
            }
        } catch (Exception ignored) {}

        if (rol == null || rol.isEmpty()) {
            if (u instanceof Administrador) rol = "Administrador";
            else if (u instanceof Empleado) rol = "Empleado";
            else if (u instanceof Cliente) rol = "Cliente";
            else rol = "";
        }
        return rol;
    }

}
