package entidades.concretas;

import java.util.List;
import entidades.enumerables.TipoRol;
import entidades.enumerables.TipoPermiso;
import sistema.SistemaBanco;

public class Membership {
    public boolean validateUser(String username, String password) {
        Usuario usuario = SistemaBanco.getInstance().getGestorUsuarios().buscarPorUsuario(username);
        return usuario != null && util.PasswordUtil.checkPassword(password, usuario.getContrasenia());
    }

    public UsuarioSistema getUser(String username) {
        Usuario usuario = SistemaBanco.getInstance().getGestorUsuarios().buscarPorUsuario(username);
        if (usuario == null) {
            System.out.print("Error al encontrar usuario.");
            return null;
        }

        TipoRol rol = usuario.getTipoRol();
        if (rol == null) {
            System.out.print("Error al encontrar rol.");
            return null;
        }

        List<TipoPermiso> permisos = usuario.getPermisos();

        return new UsuarioSistema(usuario.getNombreUsuario(), usuario.getContrasenia(), usuario, rol, permisos);
    }
}
