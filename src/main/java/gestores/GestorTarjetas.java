package gestores;

import java.sql.SQLException;
import java.util.List;
import dao.TarjetaDAO;
import entidades.concretas.Tarjeta;

public class GestorTarjetas {
    private final TarjetaDAO tarjetaDAO;

    public GestorTarjetas() {
        this.tarjetaDAO = new TarjetaDAO();
    }

    public void agregarTarjeta(Tarjeta tarjeta, String dniCliente) {
        try {
            tarjetaDAO.crearTarjeta(tarjeta, dniCliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tarjeta> listarTodos(String dniCliente) {
        try {
            return tarjetaDAO.listarTarjetasPorCliente(dniCliente);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generarNumeroTarjeta() {
        // Dummy implementation
        return "4" + (long) (Math.random() * 1000000000000000L);
    }
}
