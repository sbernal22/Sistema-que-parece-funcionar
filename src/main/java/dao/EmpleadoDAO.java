package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bd.ConexionBD;
import entidades.concretas.Empleado;

public class EmpleadoDAO {

    public void registrarEmpleado(Empleado empleado) throws SQLException {
        String sql = "INSERT INTO empleado (id, dni, nombre, apellido, telefono, direccion, nombre_usuario, contrasenia, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empleado.getId());
            stmt.setString(2, empleado.getDNI());
            stmt.setString(3, empleado.getNombre());
            stmt.setString(4, empleado.getApellido());
            stmt.setString(5, empleado.getTelefono());
            stmt.setString(6, empleado.getDireccion());
            stmt.setString(7, empleado.getNombreUsuario());
            stmt.setString(8, empleado.getContrasenia());
            stmt.setBoolean(9, empleado.getEstado());
            stmt.executeUpdate();
        }
    }

    public List<Empleado> listarEmpleados() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado";
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Empleado empleado = new Empleado(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasenia"),
                        rs.getBoolean("estado")
                );
                empleado.setId(rs.getString("id"));
                empleados.add(empleado);
            }
        }
        return empleados;
    }

    public Empleado buscarPorUsuario(String username) throws SQLException {
        String sql = "SELECT * FROM empleado WHERE nombre_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Empleado empleado = new Empleado(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("dni"),
                            rs.getString("telefono"),
                            rs.getString("direccion"),
                            rs.getString("nombre_usuario"),
                            rs.getString("contrasenia"),
                            rs.getBoolean("estado")
                    );
                    empleado.setId(rs.getString("id"));
                    return empleado;
                }
            }
        }
        return null;
    }

    public void actualizarUsuario(String dni, String nombreUsuario, String contrasenia) throws SQLException {
        String sql = "UPDATE empleado SET nombre_usuario = ?, contrasenia = ? WHERE dni = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasenia);
            stmt.setString(3, dni);
            stmt.executeUpdate();
        }
    }

    public void actualizarContrasenia(String nombreUsuario, String contrasenia) throws SQLException {
        String sql = "UPDATE empleado SET contrasenia = ? WHERE nombre_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contrasenia);
            stmt.setString(2, nombreUsuario);
            stmt.executeUpdate();
        }
    }
}
