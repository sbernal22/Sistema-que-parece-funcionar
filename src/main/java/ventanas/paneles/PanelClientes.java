package ventanas.paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import entidades.concretas.Cliente;
import entidades.concretas.Persona;
import sistema.SistemaBanco;
import gestores.GestorClientes;

public class PanelClientes extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;
    private GestorClientes gestorClientes;

    public PanelClientes() {
        setLayout(new BorderLayout());
        this.gestorClientes = SistemaBanco.getInstance().getGestorClientes();

        JToolBar toolbar = new JToolBar();
        JButton btnAgregar = new JButton("Crear");
        toolbar.add(btnAgregar);
        add(toolbar, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[] {
            "DNI", "Nombre", "Apellido", "Teléfono", "Dirección", "Usuario"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());

        actualizarClientes();
    }

    private void mostrarDialogoAgregar() {
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtUsuario = new JTextField();
        JPasswordField txtContrasenia = new JPasswordField();

        Object[] message = {
                "Nombre:", txtNombre,
                "Apellido:", txtApellido,
                "DNI:", txtDni,
                "Teléfono:", txtTelefono,
                "Dirección:", txtDireccion,
                "Nombre de usuario:", txtUsuario,
                "Contraseña:", txtContrasenia
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Crear Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String dni = txtDni.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String usuario = txtUsuario.getText().trim();
                String contrasenia = new String(txtContrasenia.getPassword()).trim();
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
                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || usuario.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Los campos Nombre, Apellido, DNI y Usuario son obligatorios.");
                    return;
                }

                Persona persona = new Persona(nombre, apellido, dni, telefono, direccion);
                Cliente cliente = new Cliente(persona, usuario, contrasenia, true);

                gestorClientes.agregarCliente(cliente);
                actualizarClientes();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear cliente: " + ex.getMessage());
            }
        }
    }

    public void actualizarClientes() {
        modelo.setRowCount(0);
        List<Cliente> lista = gestorClientes.listarTodos();
        for (Cliente c : lista) {
            String usuario = null;
            try {
                usuario = c.getNombreUsuario();
            } catch (NoSuchMethodError | AbstractMethodError ex) {
                usuario = "";
            }

            modelo.addRow(new Object[] {
                    c.getDNI(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getTelefono(),
                    c.getDireccion(),
                    usuario
            });
        }
    }
}
