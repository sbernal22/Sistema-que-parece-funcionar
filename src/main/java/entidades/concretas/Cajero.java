package entidades.concretas;

import entidades.Entidad;
import entidades.abstractas.Movimiento;
import entidades.enumerables.TipoMoneda;
import gestores.GestorMovimientos;
import interfaces.Funciones;

public class Cajero extends Entidad implements Funciones {
    private String Id;
    private boolean disponible;

    public Cajero(String Id, boolean disponible) {
        super();
        this.Id = Id;
        this.disponible = disponible;
    }

    public boolean getDisponible() {
        return this.disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getId() {
        return this.Id;
    }

    @Override
    public void depositarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor) {
        CuentaBancaria cuenta = tarjeta.getCuentaMoneda(TipoMoneda.Soles);
        if (cuenta == null) {
            System.out.println("No hay cuenta afiliada en moneda Soles.");
            return;
        }

        Movimiento deposito = new Deposito(monto, "Deposito por cajero", cuenta, this.Id);
        gestor.registrarMovimiento(deposito);
    }

    @Override
    public void retirarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor) {
        CuentaBancaria cuenta = tarjeta.getCuentaMoneda(TipoMoneda.Soles);
        if (cuenta == null) {
            System.out.println("No hay cuenta afiliada en moneda Soles.");
            return;
        }

        Movimiento retiro = new Retiro(monto, "Retiro por cajero", cuenta, this.Id);
        gestor.registrarMovimiento(retiro);
    }

    @Override
    public String toString() {
        return "Cajero [ID=" + Id + ", Disponible=" + (disponible ? "Si" : "No") + "]";
    }
}
