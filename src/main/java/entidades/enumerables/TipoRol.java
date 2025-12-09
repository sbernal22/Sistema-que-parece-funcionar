package entidades.enumerables;

public enum TipoRol {
    Administrador,
    Empleado,
    Cliente;

    public String toString() {
        return this.name();
    }
}