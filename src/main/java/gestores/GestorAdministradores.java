package gestores;

import java.sql.SQLException;
import java.util.List;
import dao.AdministradorDAO;
import entidades.concretas.Administrador;

public class GestorAdministradores {
    private final AdministradorDAO administradorDAO;

    public GestorAdministradores() {
        this.administradorDAO = new AdministradorDAO();
    }

    public void agregar(Administrador administrador) {
        try {
            administradorDAO.registrarAdministrador(administrador);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Administrador> listarTodos() {
        try {
            return administradorDAO.listarAdministradores();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public void eliminar(String dni) {
        try {
            administradorDAO.eliminarAdministrador(dni);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
