package gestores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.AdministradorDAO;
import dao.ClienteDAO;
import dao.EmpleadoDAO;
import entidades.concretas.Cliente;
import entidades.concretas.Empleado;
import entidades.concretas.LoginView;
import entidades.concretas.Usuario;

public class GestorUsuarios {
    private final ClienteDAO clienteDAO;
    private final EmpleadoDAO empleadoDAO;
    private final AdministradorDAO administradorDAO;

    public GestorUsuarios() {
        this.clienteDAO = new ClienteDAO();
        this.empleadoDAO = new EmpleadoDAO();
        this.administradorDAO = new  AdministradorDAO();
    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            usuarios.addAll(clienteDAO.listarClientes());
            usuarios.addAll(empleadoDAO.listarEmpleados());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public void listarUsuarios() {
        List<Usuario> usuarios = listarTodos();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        for (Usuario c : usuarios) {
            System.out.println("-" + c.getNombreUsuario() + " (Password:" + c.getContrasenia() + ")");
        }
    }

    public Usuario buscarPorUsuario(String username) {
        try {
            Usuario usuario = clienteDAO.buscarPorUsuario(username);
            if (usuario == null) {
                usuario = empleadoDAO.buscarPorUsuario(username);
                if(usuario == null) {
                    usuario = administradorDAO.buscarPorUsuario(username);
                }
            }
            return usuario;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void crearUsuario(String dni, LoginView usuario) {
        try {
            clienteDAO.actualizarUsuario(dni, usuario.getUsername(), usuario.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarContraseña(String username, String nueva) {
        try {
            clienteDAO.actualizarContrasenia(username, nueva);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
