package uni.usic.test.users;

import uni.usic.application.service.users.UserManager;
import uni.usic.application.service.users.UserService;
import uni.usic.domain.entity.users.enums.SignInResult;
import uni.usic.domain.entity.users.enums.SignUpResult;
import uni.usic.infrastructure.repository.users.UserFileRepository;

import java.io.IOException;

public class TestUserManager {
    private static final String TEST_USER_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/test_users.txt";
    private static final UserFileRepository userRepository = new UserFileRepository(TEST_USER_FILE_PATH);
    private static final UserService userService = new UserService(userRepository);
    private static final UserManager userManager = new UserManager(userService);

    public static void main(String[] args) throws IOException {
        System.out.println("=== Test for UserManager class ===\n");

        int totalTests = 0, passedTests = 0;

        totalTests++;
        if(testSignUpSuccess()) {
            passedTests++;
        }

        totalTests++;
        if(testSignUpMissingFields()) {
            passedTests++;
        }

        totalTests++;
        if(testSignUpDuplicate()) {
            passedTests++;
        }

        totalTests++;
        if(testSignInSuccess()) {
            passedTests++;
        }

        totalTests++;
        if(testSignInMissingFields()) {
            passedTests++;
        }

        totalTests++;
        if(testSignInWrongPassword()) {
            passedTests++;
        }

        totalTests++;
        if(testSignInUserNotFound()) {
            passedTests++;
        }

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");

    }

    private static boolean testSignUpSuccess() {
        SignUpResult result = userManager.signUp("tester", "1234", "Test Test", "test@test.com");
        return result == SignUpResult.SUCCESS;
    }

    private static boolean testSignUpMissingFields() {
        boolean case1 = userManager.signUp("", "pass", "name", "email") == SignUpResult.USERNAME_REQUIRED;
        boolean case2 = userManager.signUp("id", "", "name", "email") == SignUpResult.PASSWORD_REQUIRED;
        boolean case3 = userManager.signUp("id", "pass", "", "email") == SignUpResult.FULLNAME_REQUIRED;
        boolean case4 = userManager.signUp("id", "pass", "name", "") == SignUpResult.EMAIL_REQUIRED;
        return case1 && case2 && case3 && case4;
    }

    private static boolean testSignUpDuplicate() {
        userManager.signUp("duplicate", "pass", "name", "email@a.com");
        SignUpResult result = userManager.signUp("duplicate", "pass", "name", "email@a.com");
        return result == SignUpResult.USERNAME_ALREADY_EXISTS;
    }

    private static boolean testSignInSuccess() {
        userManager.signUp("loginuser", "1234", "User", "login@test.com");
        SignInResult result = userManager.signIn("loginuser", "1234");
        return result == SignInResult.SUCCESS;
    }

    private static boolean testSignInMissingFields() {
        boolean case1 = userManager.signIn("", "1234") == SignInResult.USERNAME_REQUIRED;
        boolean case2 = userManager.signIn("id", "") == SignInResult.PASSWORD_REQUIRED;
        return case1 && case2;
    }

    private static boolean testSignInWrongPassword() {
        userManager.signUp("user1", "correctpass", "User", "user1@test.com");
        SignInResult result = userManager.signIn("user1", "wrongpass");
        return result == SignInResult.WRONG_PASSWORD;
    }

    private static boolean testSignInUserNotFound() {
        SignInResult result = userManager.signIn("ghost", "1234");
        return result == SignInResult.USER_NOT_FOUND;
    }

    /**
     * Helper method to print success message.
     * @return
     */
    public static boolean pass() {
        System.out.println("Test Passed!\n");
        return true;
    }

    /**
     * Helper method to print failure message.
     * @param message
     * @return
     */
    public static boolean fail(String message) {
        System.out.println("Test Failed! Reason: " + message + "\n");
        return false;
    }
}
