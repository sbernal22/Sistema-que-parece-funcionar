package entidades.concretas;

public class LoginView {
    public String username;
    public String password;

    public LoginView(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}