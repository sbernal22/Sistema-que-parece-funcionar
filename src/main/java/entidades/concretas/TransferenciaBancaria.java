package entidades.concretas;

import dao.CuentaDAO;
import entidades.abstractas.Movimiento;
import java.sql.Connection;
import java.sql.SQLException;

public class TransferenciaBancaria extends Movimiento {
    private CuentaBancaria destino;

    public TransferenciaBancaria(double monto, String descripcion, CuentaBancaria origen, String encargado,
            CuentaBancaria destino) {
        super(monto, descripcion, origen, encargado);
        this.destino = destino;
    }

    public CuentaBancaria getDestino() {
        return this.destino;
    }

    @Override
    public boolean procesar(Connection conn, CuentaDAO cuentaDAO) {
        if (origen.decrementarSaldo(monto)) {
            destino.incrementarSaldo(monto);
            try {
                cuentaDAO.actualizarSaldo(origen, conn);
                cuentaDAO.actualizarSaldo(destino, conn);
                origen.getHistorial().agregarMovimiento(this);
                destino.getHistorial().agregarMovimiento(this);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                // Rollback the in-memory changes
                origen.incrementarSaldo(monto);
                destino.decrementarSaldo(monto);
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "TRANSFERENCIA: " + fecha + " - " + descripcion + " -S/ " + monto +
                " → Cuenta destino: " + destino.getNumeroCuenta();
    }
}
