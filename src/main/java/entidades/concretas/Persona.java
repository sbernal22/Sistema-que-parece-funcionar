package entidades.concretas;

import entidades.Entidad;

public class Persona extends Entidad {
    protected String nombre;
    protected String apellido;
    protected String dni;
    protected String telefono;
    protected String direccion;

    public Persona(String nombre, String apellido, String dni, String telefono, String direccion) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public String getDNI() {
        return this.dni;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDNI(String dni) {
        this.dni = dni;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void mostrarPermisos() {
        System.out.println("");
    }
}
