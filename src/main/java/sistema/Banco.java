package sistema;

import gestores.GestorCajeros;
import gestores.GestorClientes;
import gestores.GestorCuentas;
import gestores.GestorEmpleados;
import gestores.GestorMovimientos;
import gestores.GestorUsuarios;

public class Banco {

    private final GestorMovimientos gestorMovimientos;
    private final GestorClientes gestorClientes;
    private final GestorEmpleados gestorEmpleados;
    private final GestorCajeros gestorCajeros;
    private final GestorUsuarios gestorUsuarios;
    private final GestorCuentas gestorCuentas;

    public Banco() {
        this.gestorCajeros = new GestorCajeros();
        this.gestorClientes = new GestorClientes();
        this.gestorEmpleados = new GestorEmpleados();
        this.gestorMovimientos = new GestorMovimientos();
        this.gestorCuentas = new GestorCuentas();
        this.gestorUsuarios = new GestorUsuarios();
    }

    public GestorMovimientos getGestorMovimientos() {
        return this.gestorMovimientos;
    }

    public GestorClientes getGestorClientes() {
        return this.gestorClientes;
    }

    public GestorEmpleados getGestorEmpleados() {
        return this.gestorEmpleados;
    }

    public GestorCajeros getGestorCajeros() {
        return this.gestorCajeros;
    }

    public GestorUsuarios getGestorUsuarios() {
        return this.gestorUsuarios;
    }

    public GestorCuentas getGestorCuentas() {
        return this.gestorCuentas;
    }
}
