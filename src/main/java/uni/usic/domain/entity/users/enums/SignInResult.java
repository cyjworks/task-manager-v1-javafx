package uni.usic.domain.entity.users.enums;

/**
 * Enum representing possible results of a sign-in attempt.
 */
public enum SignInResult {

    /** Sign-in was successful. */
    SUCCESS,

    /** Username field is missing or empty. */
    USERNAME_REQUIRED,

    /** Password field is missing or empty. */
    PASSWORD_REQUIRED,

    /** No user found with the given username. */
    USER_NOT_FOUND,

    /** Password does not match the stored one. */
    WRONG_PASSWORD,

    /** A server error occurred during sign-in. */
    SERVER_ERROR
}
