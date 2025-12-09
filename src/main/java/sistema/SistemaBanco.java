package sistema;

import gestores.GestorCajeros;
import gestores.GestorClientes;
import gestores.GestorCuentas;
import gestores.GestorEmpleados;
import gestores.GestorMovimientos;
import gestores.GestorUsuarios;

public class SistemaBanco {
    private static SistemaBanco instance;
    private final Banco banco;

    private SistemaBanco() {
        this.banco = new Banco();
    }

    public static synchronized SistemaBanco getInstance() {
        if (instance == null) {
            instance = new SistemaBanco();
        }
        return instance;
    }

    public Banco getBanco() {
        return banco;
    }

    public GestorCajeros getGestorCajeros() {
        return banco.getGestorCajeros();
    }

    public GestorClientes getGestorClientes() {
        return banco.getGestorClientes();
    }

    public GestorEmpleados getGestorEmpleados() {
        return banco.getGestorEmpleados();
    }

    public GestorMovimientos getGestorMovimientos() {
        return banco.getGestorMovimientos();
    }

    public GestorCuentas getGestorCuentas() {
        return banco.getGestorCuentas();
    }

    public GestorUsuarios getGestorUsuarios() {
        return banco.getGestorUsuarios();
    }
}
