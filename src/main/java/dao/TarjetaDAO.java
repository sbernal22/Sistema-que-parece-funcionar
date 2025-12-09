package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bd.ConexionBD;
import entidades.concretas.Tarjeta;
import entidades.concretas.Debito;
import entidades.concretas.Credito;

public class TarjetaDAO {

    public void crearTarjeta(Tarjeta tarjeta, String dniCliente) throws SQLException {
        String sql = "INSERT INTO tarjeta (numero_tarjeta, compania, tipo, dni_cliente) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tarjeta.getNumeroTarjeta());
            stmt.setString(2, tarjeta.getCompania());
            stmt.setString(3, tarjeta instanceof Debito ? "DEBITO" : "CREDITO");
            stmt.setString(4, dniCliente);
            stmt.executeUpdate();
        }
    }

    public List<Tarjeta> listarTarjetasPorCliente(String dniCliente) throws SQLException {
        List<Tarjeta> tarjetas = new ArrayList<>();
        String sql = "SELECT * FROM tarjeta WHERE dni_cliente = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dniCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("tipo");
                    if ("DEBITO".equals(tipo)) {
                        tarjetas.add(new Debito(rs.getString("numero_tarjeta"), rs.getString("compania"), 0.0));
                    } else {
                        tarjetas.add(new Credito(rs.getString("numero_tarjeta"), rs.getString("compania")));
                    }
                }
            }
        }
        return tarjetas;
    }
}
