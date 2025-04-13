package uni.usic.domain.entity.users;

/**
 * Represents a user with authentication and personal information.
 */
public class User {
    private String username;
    private String password;
    private String fullName;
    private String email;

    /**
     * Constructs a new User with the given information.
     *
     * @param username the unique username
     * @param password the user's password
     * @param fullName the user's full name
     * @param email the user's email address
     */
    public User(String username, String password, String fullName, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
