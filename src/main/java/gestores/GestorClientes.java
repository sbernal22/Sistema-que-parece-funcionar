package gestores;

import java.sql.SQLException;
import java.util.List;
import dao.ClienteDAO;
import entidades.concretas.Cliente;

public class GestorClientes {
    private final ClienteDAO clienteDAO;

    public GestorClientes() {
        this.clienteDAO = new ClienteDAO();
    }

    public void agregarCliente(Cliente cliente) {
        try {
            clienteDAO.registrarCliente(cliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> listarTodos() {
        try {
            return clienteDAO.listarClientes();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cliente buscarCliente(String dni) {
        try {
            return clienteDAO.listarClientes().stream()
                    .filter(c -> c.getDNI().equals(dni))
                    .findFirst()
                    .orElse(null);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void listarClientes() {
        List<Cliente> clientes = listarTodos();
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Cliente c : clientes) {
            System.out.println("-" + c.getNombre() + " (DNI:" + c.getDNI() + ")");
        }
    }
}
