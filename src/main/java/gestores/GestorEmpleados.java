package gestores;

import java.sql.SQLException;
import java.util.List;
import dao.EmpleadoDAO;
import entidades.concretas.Empleado;

public class GestorEmpleados {
    private final EmpleadoDAO empleadoDAO;

    public GestorEmpleados() {
        this.empleadoDAO = new EmpleadoDAO();
    }

    public void agregarEmpleado(Empleado empleado) {
        try {
            empleadoDAO.registrarEmpleado(empleado);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Empleado> listarTodos() {
        try {
            return empleadoDAO.listarEmpleados();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Empleado buscarEmpleado(String id) {
        try {
            return empleadoDAO.listarEmpleados().stream()
                    .filter(e -> e.getId().equalsIgnoreCase(id))
                    .findFirst()
                    .orElse(null);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Empleado buscarPorDni(String dni) {
        try {
            return empleadoDAO.listarEmpleados().stream()
                    .filter(e -> e.getDNI().equals(dni))
                    .findFirst()
                    .orElse(null);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void eliminarEmpleado(String id) {
        try {
            empleadoDAO.eliminarEmpleado(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarEmpleado(Empleado empleado) {
        try {
            empleadoDAO.modificarEmpleado(empleado);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarEmpleados() {
        List<Empleado> empleados = listarTodos();
        if (empleados == null || empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        for (Empleado e : empleados) {
            System.out.println("- " + e.getNombre() + " (ID: " + e.getId() + ")");
        }
    }
}
