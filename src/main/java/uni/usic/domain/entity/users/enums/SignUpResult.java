package uni.usic.domain.entity.users.enums;

/**
 * Enum representing possible results of a sign-up attempt.
 */
public enum SignUpResult {

    /** Sign-up was successful. */
    SUCCESS,

    /** Username field is missing or empty. */
    USERNAME_REQUIRED,

    /** Password field is missing or empty. */
    PASSWORD_REQUIRED,

    /** Full name field is missing or empty. */
    FULLNAME_REQUIRED,

    /** Email field is missing or empty. */
    EMAIL_REQUIRED,

    /** Username already exists in the system. */
    USERNAME_ALREADY_EXISTS,

    /** A server error occurred during sign-up. */
    SERVER_ERROR
}
