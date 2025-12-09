package gestores;

import java.sql.SQLException;
import java.util.List;
import dao.CajeroDAO;
import entidades.concretas.Cajero;

public class GestorCajeros {
    private final CajeroDAO cajeroDAO;

    public GestorCajeros() {
        this.cajeroDAO = new CajeroDAO();
    }

    public void agregarCajero(Cajero cajero) {
        try {
            cajeroDAO.crearCajero(cajero);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cajero> listarTodos() {
        try {
            return cajeroDAO.listarCajeros();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
