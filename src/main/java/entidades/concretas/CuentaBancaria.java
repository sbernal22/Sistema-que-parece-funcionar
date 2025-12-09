package entidades.concretas;

import entidades.enumerables.TipoMoneda;
import movimientos.HistorialMovimientos;
import entidades.Entidad;

public class CuentaBancaria extends Entidad {
    private static int contadorCuentas = 1000;
    private String numeroCuenta;
    private TipoMoneda moneda;
    private double saldo;
    private String dniCliente;
    private HistorialMovimientos historial;

    public CuentaBancaria(TipoMoneda moneda, String dniCliente) {
        this.numeroCuenta = generarNumeroCuenta();
        this.moneda = moneda;
        this.saldo = 0;
        this.dniCliente = dniCliente;
        this.historial = new HistorialMovimientos();
    }

    public CuentaBancaria(String numeroCuenta, TipoMoneda moneda, double saldo, String dniCliente) {
        this.numeroCuenta = numeroCuenta;
        this.moneda = moneda;
        this.saldo = saldo;
        this.dniCliente = dniCliente;
        this.historial = new HistorialMovimientos();
    }

    private String generarNumeroCuenta() {
        return "" + contadorCuentas++;
    }

    public String getNumeroCuenta() {
        return this.numeroCuenta;
    }

    public TipoMoneda getTipoMoneda() {
        return this.moneda;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public String getDniCliente() {
        return this.dniCliente;
    }

    public HistorialMovimientos getHistorial() {
        return this.historial;
    }

    public void incrementarSaldo(double monto) {
        this.saldo += monto;
    }

    public boolean decrementarSaldo(double monto) {
        if (monto <= this.saldo) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }
}
