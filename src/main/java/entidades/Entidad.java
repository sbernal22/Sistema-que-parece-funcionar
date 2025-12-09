package entidades;

import java.time.LocalDateTime;

public abstract class Entidad {
    private String usuarioCreacion;
    private LocalDateTime fechaCreacion;
    private String usuarioModificacion;
    private LocalDateTime fechaModificacion;

    public Entidad(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Entidad() {
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void nuevaEntidad(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = LocalDateTime.now();
        this.usuarioModificacion = usuarioCreacion;
        this.fechaModificacion = LocalDateTime.now();
    }

    public void modificarEntidad(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
        this.fechaModificacion = LocalDateTime.now();
    }

    public void eliminarEntidad(String usuarioEliminacion) {
        this.usuarioModificacion = usuarioEliminacion;
        this.fechaModificacion = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Creado por: " + usuarioCreacion + " en " + fechaCreacion;
    }
}
