package entidades.concretas;

import dao.CuentaDAO;
import entidades.abstractas.Movimiento;
import java.sql.Connection;
import java.sql.SQLException;

public class Retiro extends Movimiento {
    public Retiro(double monto, String descripcion, CuentaBancaria origen, String encargado) {
        super(monto, descripcion, origen, encargado);
    }

    @Override
    public boolean procesar(Connection conn, CuentaDAO cuentaDAO) {
        if (origen.decrementarSaldo(monto)) {
            try {
                cuentaDAO.actualizarSaldo(origen, conn);
                origen.getHistorial().agregarMovimiento(this);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                // Rollback the in-memory change
                origen.incrementarSaldo(monto);
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "RETIRO: " + fecha + " - " + descripcion + " -S/ " + monto;
    }
}
