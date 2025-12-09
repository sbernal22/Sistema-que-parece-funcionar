package entidades.concretas;

import java.util.List;

import entidades.enumerables.TipoRol;
import entidades.enumerables.TipoPermiso;

public class UsuarioSistema {
    private String usuario;
    private String contrasenia;
    private Persona persona;
    private TipoRol rol;
    private List<TipoPermiso> permisos;

    public UsuarioSistema(String usuario, String contrasenia, Persona persona, TipoRol rol,
            List<TipoPermiso> permisos) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.persona = persona;
        this.rol = rol;
        this.permisos = permisos;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public Persona getPersona() {
        return persona;
    }

    public TipoRol getRol() {
        return rol;
    }

    public void setRol(TipoRol rol) {
        this.rol = rol;
    }

    public List<TipoPermiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<TipoPermiso> permisos) {
        this.permisos = permisos;
    }
}
