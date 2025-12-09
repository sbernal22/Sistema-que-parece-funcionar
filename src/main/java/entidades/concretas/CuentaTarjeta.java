package entidades.concretas;

public class CuentaTarjeta {
    private Tarjeta tarjeta;
    private CuentaBancaria cuenta;

    public CuentaTarjeta(Tarjeta tarjeta, CuentaBancaria cuenta) {
        this.tarjeta = tarjeta;
        this.cuenta = cuenta;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public CuentaBancaria getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }

}