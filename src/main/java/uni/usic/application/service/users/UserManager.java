package uni.usic.application.service.users;

import uni.usic.domain.entity.users.User;
import uni.usic.domain.entity.users.enums.SignInResult;
import uni.usic.domain.entity.users.enums.SignUpResult;

/**
 * Handles user-related operations such as sign-up, sign-in, and validation.
 */
public class UserManager {
    private final UserService userService;

    /**
     * Constructs a UserManager with the given UserService.
     *
     * @param service the user service to use
     */
    public UserManager(UserService service) {
        this.userService = service;
    }

    /**
     * Registers a new user with the given information.
     *
     * @param username the desired username
     * @param password the desired password
     * @param fullName the full name of the user
     * @param email the email address of the user
     * @return the result of the sign-up process
     */
    public SignUpResult signUp(String username, String password, String fullName, String email) {
        SignUpResult validationResult = validateSignUp(username, password, fullName, email);
        if (validationResult != SignUpResult.SUCCESS) return validationResult;

        User existUser = userService.findByUsername(username);
        if (existUser != null) return SignUpResult.USERNAME_ALREADY_EXISTS;

        User user = userService.signUp(username, password, fullName, email);
        return user != null ? SignUpResult.SUCCESS : SignUpResult.SERVER_ERROR;
    }

    /**
     * Attempts to authenticate a user with the given credentials.
     *
     * @param username the entered username
     * @param password the entered password
     * @return the result of the sign-in attempt
     */
    public SignInResult signIn(String username, String password) {
        SignInResult validationResult = validateSignIn(username, password);
        if (validationResult != SignInResult.SUCCESS) return validationResult;

        System.out.println(username + "/" + password);
        User existUser = userService.findByUsername(username);
        if (existUser == null) return SignInResult.USER_NOT_FOUND;

        if (!existUser.getPassword().equals(password)) return SignInResult.WRONG_PASSWORD;

        return SignInResult.SUCCESS;
    }

    /**
     * Validates the fields for user registration.
     *
     * @param username the username
     * @param password the password
     * @param fullName the full name
     * @param email the email
     * @return the result of the validation
     */
    private SignUpResult validateSignUp(String username, String password, String fullName, String email) {
        if (isEmpty(username)) return SignUpResult.USERNAME_REQUIRED;
        if (isEmpty(password)) return SignUpResult.PASSWORD_REQUIRED;
        if (isEmpty(fullName)) return SignUpResult.FULLNAME_REQUIRED;
        if (isEmpty(email)) return SignUpResult.EMAIL_REQUIRED;
        return SignUpResult.SUCCESS;
    }

    /**
     * Validates the fields for user login.
     *
     * @param username the entered username
     * @param password the entered password
     * @return the result of the validation
     */
    private SignInResult validateSignIn(String username, String password) {
        if (isEmpty(username)) return SignInResult.USERNAME_REQUIRED;
        if (isEmpty(password)) return SignInResult.PASSWORD_REQUIRED;
        return SignInResult.SUCCESS;
    }

    /**
     * Checks if a string is null or empty.
     *
     * @param str the string to check
     * @return true if the string is empty or null, false otherwise
     */
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Finds and returns a user by username.
     *
     * @param username the username to search
     * @return the matching User or null if not found
     */
    public User findByUsername(String username) {
        return userService.findByUsername(username);
    }
}
