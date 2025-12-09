package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bd.ConexionBD;
import entidades.concretas.CuentaBancaria;
import entidades.enumerables.TipoMoneda;

public class CuentaDAO {

    public void crearCuenta(CuentaBancaria cuenta) throws SQLException {
        String sql = "INSERT INTO cuenta (numero_cuenta, moneda, saldo, dni_cliente) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cuenta.getNumeroCuenta());
            stmt.setString(2, cuenta.getTipoMoneda().name());
            stmt.setDouble(3, cuenta.getSaldo());
            stmt.setString(4, cuenta.getDniCliente());
            stmt.executeUpdate();
        }
    }

    public List<CuentaBancaria> listarCuentas() throws SQLException {
        List<CuentaBancaria> cuentas = new ArrayList<>();
        String sql = "SELECT * FROM cuenta";
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CuentaBancaria cuenta = new CuentaBancaria(
                        rs.getString("numero_cuenta"),
                        TipoMoneda.valueOf(rs.getString("moneda")),
                        rs.getDouble("saldo"),
                        rs.getString("dni_cliente")
                );
                cuentas.add(cuenta);
            }
        }
        return cuentas;
    }

    public CuentaBancaria buscarPorNumero(String numeroCuenta) throws SQLException {
        String sql = "SELECT * FROM cuenta WHERE numero_cuenta = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroCuenta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new CuentaBancaria(
                            rs.getString("numero_cuenta"),
                            TipoMoneda.valueOf(rs.getString("moneda")),
                            rs.getDouble("saldo"),
                            rs.getString("dni_cliente")
                    );
                }
            }
        }
        return null;
    }

    public void actualizarSaldo(CuentaBancaria cuenta, Connection conn) throws SQLException {
        String sql = "UPDATE cuenta SET saldo = ? WHERE numero_cuenta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, cuenta.getSaldo());
            stmt.setString(2, cuenta.getNumeroCuenta());
            stmt.executeUpdate();
        }
    }
}
