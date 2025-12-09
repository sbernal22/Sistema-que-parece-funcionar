package movimientos;

import java.util.ArrayList;

import entidades.abstractas.Movimiento;

public class HistorialMovimientos {
    private ArrayList<Movimiento> movimientos;

    public HistorialMovimientos() {
        this.movimientos = new ArrayList<>();
    }
    public ArrayList<Movimiento> getMovimientos() {
        return this.movimientos;
    }

    public void agregarMovimiento(Movimiento movimiento) {
        movimientos.add(movimiento);
    }

    public void listarMovimientos() {
        for (Movimiento m : movimientos) {
            System.out.println(m);
        }
    }
}
