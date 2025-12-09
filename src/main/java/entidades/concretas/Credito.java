package entidades.concretas;

public class Credito extends Tarjeta {
    private double deudaActual;
    private boolean bloqueada;

    public Credito(String numeroTarjeta, String compañia) {
        super(numeroTarjeta, compañia);
        this.deudaActual = 0;
        this.bloqueada = false;
    }

    public double getDeudaActual() {
        return this.deudaActual;
    }

    public boolean isBloqueada() {
        return this.bloqueada;
    }

    public void bloquearTarjeta() {
        this.bloqueada = true;
    }

    public void pagarDeuda(double monto) {
        this.deudaActual -= monto;
    }
}
