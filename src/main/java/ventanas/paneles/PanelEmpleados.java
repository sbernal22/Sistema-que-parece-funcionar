package ventanas.paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import entidades.concretas.Empleado;
import entidades.concretas.Persona;
import gestores.GestorEmpleados;
import sistema.SistemaBanco;

public class PanelEmpleados extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;
    private GestorEmpleados gestorEmpleados;

    public PanelEmpleados() {
        setLayout(new BorderLayout());
        this.gestorEmpleados = SistemaBanco.getInstance().getGestorEmpleados();

        JToolBar toolbar = new JToolBar();
        JButton btnAgregar = new JButton("Contratar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        toolbar.add(btnModificar);
        toolbar.add(btnEliminar);
        toolbar.add(btnAgregar);
        add(toolbar, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[] { "ID", "DNI", "Nombre", "Apellido", "Teléfono", "Dirección" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        btnModificar.addActionListener(e -> modificarEmpleado());

        actualizarEmpleados();
    }

    private void mostrarDialogoAgregar() {
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtDireccion = new JTextField();

        Object[] message = {
                "Nombre:", txtNombre,
                "Apellido:", txtApellido,
                "DNI:", txtDni,
                "Teléfono:", txtTelefono,
                "Dirección:", txtDireccion
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Contratar Empleado", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String dni = txtDni.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String direccion = txtDireccion.getText().trim();
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
                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos obligatorios deben ser llenados.");
                    return;
                }

                Persona persona = new Persona(nombre, apellido, dni, telefono, direccion);

                Empleado empleado = new Empleado(persona, dni, dni, true);

                gestorEmpleados.agregarEmpleado(empleado);
                actualizarEmpleados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear empleado: " + ex.getMessage());
            }
        }
    }
    private void modificarEmpleado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado.");
            return;
        }

        String id = modelo.getValueAt(fila, 0).toString();
        Empleado emp = gestorEmpleados.buscarEmpleado(id);

        if (emp == null) {
            JOptionPane.showMessageDialog(this, "Error: empleado no encontrado.");
            return;
        }

        JTextField txtNombre = new JTextField(emp.getNombre());
        JTextField txtApellido = new JTextField(emp.getApellido());
        JTextField txtTelefono = new JTextField(emp.getTelefono());
        JTextField txtDireccion = new JTextField(emp.getDireccion());

        Object[] msg = {
                "Nombre:", txtNombre,
                "Apellido:", txtApellido,
                "Teléfono:", txtTelefono,
                "Dirección:", txtDireccion
        };

        int option = JOptionPane.showConfirmDialog(this, msg, "Modificar Empleado", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            emp.setNombre(txtNombre.getText().trim());
            emp.setApellido(txtApellido.getText().trim());
            emp.setTelefono(txtTelefono.getText().trim());
            emp.setDireccion(txtDireccion.getText().trim());

            gestorEmpleados.modificarEmpleado(emp);
            actualizarEmpleados();
        }
    }
    private void eliminarEmpleado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado.");
            return;
        }

        String id = modelo.getValueAt(fila, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar este empleado?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            gestorEmpleados.eliminarEmpleado(id);
            actualizarEmpleados();
        }
    }



    public void actualizarEmpleados() {
        modelo.setRowCount(0);
        List<Empleado> lista = gestorEmpleados.listarTodos();
        for (Empleado e : lista) {
            modelo.addRow(new Object[] {
                    e.getId(),
                    e.getDNI(),
                    e.getNombre(),
                    e.getApellido(),
                    e.getTelefono(),
                    e.getDireccion()
            });
        }
    }
}
