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
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnModificar = new JButton("Modificar");

        toolbar.add(btnEliminar);
        toolbar.add(btnModificar);

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
        btnEliminar.addActionListener(e -> eliminarClienteSeleccionado());
        btnModificar.addActionListener(e -> modificarCliente());


        actualizarClientes();
    }
    private void eliminarClienteSeleccionado() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un cliente para eliminar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String dni = modelo.getValueAt(fila, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar al cliente con DNI " + dni + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean eliminado = gestorClientes.eliminar(dni);

        if (!eliminado) {
            JOptionPane.showMessageDialog(this,
                    "No se puede eliminar un cliente con cuentas, tarjetas o préstamos asociados.",
                    "Eliminación no permitida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Cliente eliminado correctamente.");

        actualizarClientes();
    }


    private void modificarCliente() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para modificar.");
            return;
        }

        String dni = (String) tabla.getValueAt(fila, 0);
        Cliente cliente = gestorClientes.buscarCliente(dni);

        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el cliente en la base de datos.");
            return;
        }

        JTextField txtNombre = new JTextField(cliente.getNombre());
        JTextField txtApellido = new JTextField(cliente.getApellido());
        JTextField txtTelefono = new JTextField(cliente.getTelefono());
        JTextField txtDireccion = new JTextField(cliente.getDireccion());
        JTextField txtUsuario = new JTextField(cliente.getNombreUsuario());
        JPasswordField txtContrasenia = new JPasswordField(cliente.getContrasenia());

        Object[] message = {
                "Nombre:", txtNombre,
                "Apellido:", txtApellido,
                "Teléfono:", txtTelefono,
                "Dirección:", txtDireccion,
                "Usuario:", txtUsuario,
                "Contraseña:", txtContrasenia
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Modificar Cliente", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String usuario = txtUsuario.getText().trim();
                String contrasenia = new String(txtContrasenia.getPassword()).trim();

                // Validaciones (las mismas que crear)
                if (nombre.length() < 2 || !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    JOptionPane.showMessageDialog(this, "Nombre inválido");
                    return;
                }
                if (apellido.length() < 2 || !apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    JOptionPane.showMessageDialog(this, "Apellido inválido");
                    return;
                }
                if (!telefono.isEmpty() && !telefono.matches("\\d{9}")) {
                    JOptionPane.showMessageDialog(this, "Teléfono inválido");
                    return;
                }
                if (usuario.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Usuario obligatorio");
                    return;
                }

                // Actualizar objeto
                Persona p = new Persona(nombre, apellido, dni, telefono, direccion);
                Cliente actualizado = new Cliente(p, usuario, contrasenia, cliente.getEstado());

                gestorClientes.actualizarCliente(actualizado);
                actualizarClientes();

                JOptionPane.showMessageDialog(this, "Cliente modificado correctamente.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al modificar cliente: " + ex.getMessage());
            }
        }
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
