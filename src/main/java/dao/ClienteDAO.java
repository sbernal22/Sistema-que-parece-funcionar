package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bd.ConexionBD;
import entidades.concretas.Cliente;

public class ClienteDAO {

    public void registrarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (dni, nombre, apellido, telefono, direccion, nombre_usuario, contrasenia, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getDNI());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellido());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getDireccion());
            stmt.setString(6, cliente.getNombreUsuario());
            stmt.setString(7, cliente.getContrasenia());
            stmt.setBoolean(8, cliente.getEstado());
            stmt.executeUpdate();
        }
    }

    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasenia"),
                        rs.getBoolean("estado")
                );
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public Cliente buscarPorUsuario(String username) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE nombre_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
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
    public boolean eliminarCliente(String dni) {
        try (Connection conn = ConexionBD.getConexion()) {

            // 1. Verificar si tiene TARJETAS
            String sqlTarjetas = "SELECT COUNT(*) FROM tarjeta WHERE dni_cliente = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlTarjetas)) {
                stmt.setString(1, dni);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("Tiene tarjetas asociadas");
                        return false; // No se puede eliminar
                    }
                }
            }

            // 2. Verificar si tiene CUENTAS
            String sqlCuentas = "SELECT COUNT(*) FROM cuenta WHERE dni_cliente = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlCuentas)) {
                stmt.setString(1, dni);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("Tiene cuentas asociadas");
                        return false;
                    }
                }
            }

            // 3. Verificar si tiene PRÉSTAMOS
            String sqlPrestamos = "SELECT COUNT(*) FROM prestamo WHERE dni_cliente = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlPrestamos)) {
                stmt.setString(1, dni);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("Tiene préstamos asociados");
                        return false;
                    }
                }
            }

            String sqlEliminar = "DELETE FROM cliente WHERE dni = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlEliminar)) {
                stmt.setString(1, dni);
                stmt.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void actualizarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nombre=?, apellido=?, telefono=?, direccion=?, nombre_usuario=?, contrasenia=? WHERE dni=?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getDireccion());
            stmt.setString(5, cliente.getNombreUsuario());
            stmt.setString(6, cliente.getContrasenia());
            stmt.setString(7, cliente.getDNI());

            stmt.executeUpdate();
        }
    }


    public void actualizarUsuario(String dni, String nombreUsuario, String contrasenia) throws SQLException {
        String sql = "UPDATE cliente SET nombre_usuario = ?, contrasenia = ? WHERE dni = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasenia);
            stmt.setString(3, dni);
            stmt.executeUpdate();
        }
    }

    public void actualizarContrasenia(String nombreUsuario, String contrasenia) throws SQLException {
        String sql = "UPDATE cliente SET contrasenia = ? WHERE nombre_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contrasenia);
            stmt.setString(2, nombreUsuario);
            stmt.executeUpdate();
        }
    }
}
