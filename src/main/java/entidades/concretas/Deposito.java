package entidades.concretas;

import dao.CuentaDAO;
import entidades.abstractas.Movimiento;
import java.sql.Connection;
import java.sql.SQLException;

public class Deposito extends Movimiento {

    public Deposito(double monto, String descripcion, CuentaBancaria origen, String encargado) {
        super(monto, descripcion, origen, encargado);
    }

    @Override
    public boolean procesar(Connection conn, CuentaDAO cuentaDAO) {
        origen.incrementarSaldo(monto);
        try {
            cuentaDAO.actualizarSaldo(origen, conn);
            origen.getHistorial().agregarMovimiento(this);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            // Rollback the in-memory change
            origen.decrementarSaldo(monto);
            return false;
        }
    }

    @Override
    public String toString() {
        return "DEPOSITO: " + fecha + " - " + descripcion + " +S/ " + monto;
    }
}
