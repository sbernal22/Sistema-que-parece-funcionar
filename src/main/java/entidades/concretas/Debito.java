package entidades.concretas;

public class Debito extends Tarjeta {
    private double interes;

    public Debito(String numeroTarjeta, String compañia, double interes) {
        super(numeroTarjeta, compañia);
        this.interes = interes;
    }

    public double getInteres() {
        return this.interes;
    }

    public void aplicarInteres() {
    }
}
