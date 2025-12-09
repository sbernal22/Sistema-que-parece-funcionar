package sistema;

import entidades.concretas.Membership;
import entidades.concretas.SessionManager;
import entidades.concretas.Usuario;
import entidades.concretas.UsuarioSistema;

public class SistemaLogin {
    private Membership membership;

    public SistemaLogin() {
        this.membership = new Membership();
    }

    public Usuario login(String username, String password) throws Exception {
        if (membership.validateUser(username, password)) {
            UsuarioSistema usuarioSistema = membership.getUser(username);
            if (usuarioSistema != null) {
                SessionManager.setCurrentUser(usuarioSistema);
                Usuario usuario = SistemaBanco.getInstance().getBanco().getGestorUsuarios().buscarPorUsuario(username);
                if (usuario != null) {
                    return usuario;
                } else {
                    throw new Exception("Usuario validado pero no encontrado en el banco de datos.");
                }
            } else {
                throw new Exception("Error al recuperar datos del usuario.");
            }
        } else {
            throw new Exception("Credenciales incorrectas.");
        }
    }

    public void logout() {
        SessionManager.setCurrentUser(null);
    }
}
