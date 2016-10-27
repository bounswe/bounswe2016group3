package bounswegroup3.model;

/**
 * Represents the usernsame/password pair one uses to log
 * onto the application. It is never returned by the app,
 * but the login call requires this object as input.
 * <br>
 * <code>{"username":String, "password":String}</code>
 */
public class LoginCredentials {
    private String email;
    private String password;

    public LoginCredentials() {
    }

    public LoginCredentials(String username, String password) {
        super();
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}