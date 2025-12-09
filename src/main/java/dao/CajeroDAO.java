package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bd.ConexionBD;
import entidades.concretas.Cajero;

public class CajeroDAO {

    public void crearCajero(Cajero cajero) throws SQLException {
        String sql = "INSERT INTO cajero (id, disponible) VALUES (?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cajero.getId());
            stmt.setBoolean(2, cajero.getDisponible());
            stmt.executeUpdate();
        }
    }

    public List<Cajero> listarCajeros() throws SQLException {
        List<Cajero> cajeros = new ArrayList<>();
        String sql = "SELECT * FROM cajero";
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cajeros.add(new Cajero(rs.getString("id"), rs.getBoolean("disponible")));
            }
        }
        return cajeros;
    }
}
