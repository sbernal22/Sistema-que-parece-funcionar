package gestores;

import java.sql.Connection;
import java.sql.SQLException;
import bd.ConexionBD;
import dao.CuentaDAO;
import dao.TransaccionDAO;
import entidades.abstractas.Movimiento;

public class GestorMovimientos {
    private final TransaccionDAO transaccionDAO;
    private final CuentaDAO cuentaDAO;

    public GestorMovimientos() {
        this.transaccionDAO = new TransaccionDAO();
        this.cuentaDAO = new CuentaDAO();
    }

    public void registrarMovimiento(Movimiento movimiento) {
        Connection conn = null;
        try {
            conn = ConexionBD.getConexion();
            conn.setAutoCommit(false);

            if (movimiento.procesar(conn, cuentaDAO)) {
                transaccionDAO.registrarTransaccion(movimiento, conn);
                conn.commit();
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
