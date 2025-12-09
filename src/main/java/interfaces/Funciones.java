package interfaces;

import entidades.concretas.Tarjeta;
import gestores.GestorMovimientos;

public interface Funciones {
    void depositarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor);

    void retirarConTarjeta(Tarjeta tarjeta, double monto, GestorMovimientos gestor);
}
