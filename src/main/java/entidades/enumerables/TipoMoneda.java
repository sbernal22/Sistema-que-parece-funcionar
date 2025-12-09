package entidades.enumerables;

public enum TipoMoneda {
    Soles,
    Dolares,
    Euros;

    public String toString() {
        return this.name();
    }
}
/*
 * public enum TipoMoneda {
 * SOLES("Soles", "S/", "PEN"),
 * DOLARES("Dólares", "$", "USD"),
 * EUROS("Euros", "€", "EUR");
 * 
 * private String nombre;
 * private String simbolo;
 * private String codigo;
 * 
 * TipoMoneda(String nombre, String simbolo, String codigo) {
 * this.nombre = nombre;
 * this.simbolo = simbolo;
 * this.codigo = codigo;
 * }
 * 
 * public String getNombre() {
 * return nombre;
 * }
 * 
 * public String getSimbolo() {
 * return simbolo;
 * }
 * 
 * public String getCodigo() {
 * return codigo;
 * }
 * }
 */
