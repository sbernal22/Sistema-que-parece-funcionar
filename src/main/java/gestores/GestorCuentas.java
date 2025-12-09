package gestores;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import bd.ConexionBD;
import dao.CuentaDAO;
import entidades.concretas.CuentaBancaria;

public class GestorCuentas {
    private final CuentaDAO cuentaDAO;

    public GestorCuentas() {
        this.cuentaDAO = new CuentaDAO();
    }

    public void agregarCuenta(CuentaBancaria cuenta) {
        try {
            cuentaDAO.crearCuenta(cuenta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CuentaBancaria> listarTodos() {
        try {
            return cuentaDAO.listarCuentas();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CuentaBancaria buscarCuenta(String numeroCuenta) {
        try {
            return cuentaDAO.buscarPorNumero(numeroCuenta);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void listarCuentas() {
        List<CuentaBancaria> cuentas = listarTodos();
        if (cuentas == null || cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas.");
            return;
        }
        for (CuentaBancaria c : cuentas) {
            System.out.println("- Numero de cuenta: " + c.getNumeroCuenta() + ". Saldo: " + c.getSaldo() + "("
                    + c.getTipoMoneda() + ")");
        }
    }

    public List<CuentaBancaria> getCuentas() {
        return listarTodos();
    }

    public void actualizarSaldo(CuentaBancaria cuenta) {
        try (Connection conn = ConexionBD.getConexion()) {
            cuentaDAO.actualizarSaldo(cuenta, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
