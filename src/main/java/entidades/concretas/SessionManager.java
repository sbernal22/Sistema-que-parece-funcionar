package entidades.concretas;

public class SessionManager {
    private static UsuarioSistema currentUser;
    private static boolean sessionActive;

    public static void setCurrentUser(UsuarioSistema user) {
        currentUser = user;
    }

    public static UsuarioSistema getCurrentUser() {
        return currentUser;
    }
}