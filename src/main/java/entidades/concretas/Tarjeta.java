package entidades.concretas;

import java.util.ArrayList;
import java.util.HashMap;

import entidades.enumerables.TipoMoneda;
import entidades.Entidad;

public class Tarjeta extends Entidad {
    protected String numeroTarjeta;
    protected String compania;
    protected HashMap<TipoMoneda, CuentaBancaria> mapCuentasMoneda;

    public Tarjeta(String numeroTarjeta, String compania) {
        this.numeroTarjeta = numeroTarjeta;
        this.compania = compania;
        this.mapCuentasMoneda = new HashMap<>();
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public void afiliarCuenta(CuentaBancaria cuenta) {
        mapCuentasMoneda.put(cuenta.getTipoMoneda(), cuenta);
    }

    public CuentaBancaria getCuentaMoneda(TipoMoneda moneda) {
        return mapCuentasMoneda.get(moneda);
    }

    public ArrayList<CuentaBancaria> getCuentasAfiliadas() {
        return new ArrayList<>(this.mapCuentasMoneda.values());
    }

    @Override
    public String toString() {
        return "Numero: " + numeroTarjeta + ", Compania: " + compania;
    }
}
