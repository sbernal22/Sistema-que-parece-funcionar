package sistema;

import entidades.enumerables.TipoPermiso;
import gestores.*;

import java.util.List;

public class Banco {

    private final GestorMovimientos gestorMovimientos;
    private final GestorClientes gestorClientes;
    private final GestorEmpleados gestorEmpleados;
    private final GestorCajeros gestorCajeros;
    private final GestorUsuarios gestorUsuarios;
    private GestorPermisos gestorPermisos;
    private final GestorCuentas gestorCuentas;

    public Banco() {
        this.gestorCajeros = new GestorCajeros();
        this.gestorClientes = new GestorClientes();
        this.gestorEmpleados = new GestorEmpleados();
        this.gestorMovimientos = new GestorMovimientos();
        this.gestorCuentas = new GestorCuentas();
        this.gestorUsuarios = new GestorUsuarios();
        this.gestorPermisos = new GestorPermisos();
        inicializarPermisos();
    }
    private void inicializarPermisos() {
        gestorPermisos.agregarPermisos(List.of(TipoPermiso.values()));
        gestorPermisos.modificarPermisosByRol("Administrador",
                List.of(TipoPermiso.values()));
        gestorPermisos.modificarPermisosByRol("Empleado",
                List.of(TipoPermiso.TARJ, TipoPermiso.CUEN, TipoPermiso.MOVI, TipoPermiso.CLIE));
        gestorPermisos.modificarPermisosByRol("Cliente", List.of(TipoPermiso.TARJ, TipoPermiso.CUEN, TipoPermiso.MOVI));
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
