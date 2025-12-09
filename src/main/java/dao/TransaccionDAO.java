package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import bd.ConexionBD;
import entidades.abstractas.Movimiento;
import entidades.concretas.Deposito;
import entidades.concretas.Retiro;
import entidades.concretas.TransferenciaBancaria;

public class TransaccionDAO {

    public void registrarTransaccion(Movimiento movimiento, Connection conn) throws SQLException {
        String sql = "INSERT INTO transaccion (monto, descripcion, tipo, cuenta_origen_numero, cuenta_destino_numero, empleado_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, movimiento.getMonto());
            stmt.setString(2, movimiento.getDescripcion());

            if (movimiento instanceof Deposito) {
                stmt.setString(3, "DEPOSITO");
                stmt.setString(4, movimiento.getOrigen().getNumeroCuenta());
                stmt.setNull(5, java.sql.Types.VARCHAR);
                stmt.setString(6, movimiento.getEncargado());
            } else if (movimiento instanceof Retiro) {
                stmt.setString(3, "RETIRO");
                stmt.setString(4, movimiento.getOrigen().getNumeroCuenta());
                stmt.setNull(5, java.sql.Types.VARCHAR);
                stmt.setString(6, movimiento.getEncargado());
            } else if (movimiento instanceof TransferenciaBancaria) {
                stmt.setString(3, "TRANSFERENCIA");
                stmt.setString(4, movimiento.getOrigen().getNumeroCuenta());
                stmt.setString(5, ((TransferenciaBancaria) movimiento).getDestino().getNumeroCuenta());
                stmt.setString(6, movimiento.getEncargado());
            }

            stmt.executeUpdate();
        }
    }
}
