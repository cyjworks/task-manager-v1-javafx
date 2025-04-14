package uni.usic.application.service.users;

import uni.usic.domain.entity.users.User;

/**
 * Interface defining user-related operations such as sign-up, sign-in, and user lookup.
 */
public interface UserOperations {

    /**
     * Registers a new user with the provided information.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param fullName the full name of the user
     * @param email the email address of the user
     * @return the created User object, or null if registration failed
     */
    User signUp(String username, String password, String fullName, String email);

    /**
     * Checks user credentials for authentication.
     *
     * @param username the username input
     * @param password the password input
     * @return true if credentials are valid, false otherwise
     */
    boolean signIn(String username, String password);

    /**
     * Finds a user by username.
     *
     * @param username the username to search for
     * @return the User object if found, otherwise null
     */
    User findByUsername(String username);
}
