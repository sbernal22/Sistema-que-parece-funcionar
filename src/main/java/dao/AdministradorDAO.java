package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bd.ConexionBD;
import entidades.concretas.Administrador;
import entidades.concretas.Cliente;

public class AdministradorDAO {

    public void registrarAdministrador(Administrador admin) throws SQLException {
        String sql = "INSERT INTO administrador (dni, nombre, apellido, telefono, direccion, nombre_usuario, contrasenia, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getDNI());
            stmt.setString(2, admin.getNombre());
            stmt.setString(3, admin.getApellido());
            stmt.setString(4, admin.getTelefono());
            stmt.setString(5, admin.getDireccion());
            stmt.setString(6, admin.getNombreUsuario());
            stmt.setString(7, admin.getContrasenia());
            stmt.setBoolean(8, admin.getEstado());
            stmt.executeUpdate();
        }
    }

    public List<Administrador> listarAdministradores() throws SQLException {
        List<Administrador> administradores = new ArrayList<>();
        String sql = "SELECT * FROM administrador";
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Administrador admin = new Administrador(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasenia"),
                        rs.getBoolean("estado")
                );
                administradores.add(admin);
            }
        }
        return administradores;
    }
    public Administrador buscarPorUsuario(String username) throws SQLException {
        String sql = "SELECT * FROM administrador WHERE nombre_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Administrador(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("dni"),
                            rs.getString("telefono"),
                            rs.getString("direccion"),
                            rs.getString("nombre_usuario"),
                            rs.getString("contrasenia"),
                            rs.getBoolean("estado")
                    );
                }
            }
        }
        return null;
    }
    public void eliminarAdministrador(String dni) throws SQLException {
        String sql = "DELETE FROM administrador WHERE dni = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.executeUpdate();
        }
    }

}
