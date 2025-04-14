package uni.usic.test.users;

import uni.usic.application.service.users.UserManager;
import uni.usic.application.service.users.UserService;
import uni.usic.domain.entity.users.enums.SignInResult;
import uni.usic.domain.entity.users.enums.SignUpResult;
import uni.usic.infrastructure.repository.users.UserFileRepository;

import java.io.File;

public class TestUserManager {
    private static final String TEST_USER_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/test_users.txt";
    private static final UserFileRepository userRepository = new UserFileRepository(TEST_USER_FILE_PATH);
    private static final UserService userService = new UserService(userRepository);
    private static final UserManager userManager = new UserManager(userService);

    public static void main(String[] args) {
        System.out.println("=== Test for UserManager class ===\n");

        cleanTestFile();

        int totalTests = 0, passedTests = 0;

        totalTests++;
        if (testSignUpSuccess()) passedTests++;

        totalTests++;
        if (testSignUpMissingFields()) passedTests++;

        totalTests++;
        if (testSignUpDuplicate()) passedTests++;

        totalTests++;
        if (testSignInSuccess()) passedTests++;

        totalTests++;
        if (testSignInMissingFields()) passedTests++;

        totalTests++;
        if (testSignInWrongPassword()) passedTests++;

        totalTests++;
        if (testSignInUserNotFound()) passedTests++;

        totalTests++;
        if (testFindByUsername()) passedTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");

        cleanTestFile();
    }

    public static boolean testSignUpSuccess() {
        System.out.println("Running testSignUpSuccess()...");
        SignUpResult result = userManager.signUp("tester", "1234", "Test User", "test@a.com");
        if (result != SignUpResult.SUCCESS) return fail("Expected SUCCESS but got " + result);
        return pass();
    }

    public static boolean testSignUpMissingFields() {
        System.out.println("Running testSignUpMissingFields()...");
        boolean case1 = userManager.signUp("", "pass", "name", "email") == SignUpResult.USERNAME_REQUIRED;
        boolean case2 = userManager.signUp("id", "", "name", "email") == SignUpResult.PASSWORD_REQUIRED;
        boolean case3 = userManager.signUp("id", "pass", "", "email") == SignUpResult.FULLNAME_REQUIRED;
        boolean case4 = userManager.signUp("id", "pass", "name", "") == SignUpResult.EMAIL_REQUIRED;

        if (!case1) return fail("Missing username case failed");
        if (!case2) return fail("Missing password case failed");
        if (!case3) return fail("Missing full name case failed");
        if (!case4) return fail("Missing email case failed");

        return pass();
    }

    public static boolean testSignUpDuplicate() {
        System.out.println("Running testSignUpDuplicate()...");
        userManager.signUp("duplicate", "pass", "name", "email@a.com");
        SignUpResult result = userManager.signUp("duplicate", "pass", "name", "email@a.com");
        if (result != SignUpResult.USERNAME_ALREADY_EXISTS) return fail("Expected USERNAME_ALREADY_EXISTS but got " + result);
        return pass();
    }

    public static boolean testSignInSuccess() {
        System.out.println("Running testSignInSuccess()...");
        userManager.signUp("loginuser", "1234", "User", "login@test.com");
        SignInResult result = userManager.signIn("loginuser", "1234");
        if (result != SignInResult.SUCCESS) return fail("Expected SUCCESS but got " + result);
        return pass();
    }

    public static boolean testSignInMissingFields() {
        System.out.println("Running testSignInMissingFields()...");
        boolean case1 = userManager.signIn("", "pass") == SignInResult.USERNAME_REQUIRED;
        boolean case2 = userManager.signIn("id", "") == SignInResult.PASSWORD_REQUIRED;

        if (!case1) return fail("Missing username case failed");
        if (!case2) return fail("Missing password case failed");

        return pass();
    }

    public static boolean testSignInWrongPassword() {
        System.out.println("Running testSignInWrongPassword()...");
        userManager.signUp("user1", "correctpass", "User", "user1@test.com");
        SignInResult result = userManager.signIn("user1", "wrongpass");
        if (result != SignInResult.WRONG_PASSWORD) return fail("Expected WRONG_PASSWORD but got " + result);
        return pass();
    }

    public static boolean testSignInUserNotFound() {
        System.out.println("Running testSignInUserNotFound()...");
        SignInResult result = userManager.signIn("ghostuser", "pass");
        if (result != SignInResult.USER_NOT_FOUND) return fail("Expected USER_NOT_FOUND but got " + result);
        return pass();
    }

    /**
     * Test finding a user by username after successful signup.
     * @return test result
     */
    public static boolean testFindByUsername() {
        System.out.println("Running testFindByUsername()...");

        String username = "findme";
        String password = "findpass";
        String fullName = "Find User";
        String email = "find@user.com";

        userManager.signUp(username, password, fullName, email);

        var user = userManager.findByUsername(username);

        if (user == null) return fail("User should be found but was null.");
        if (!user.getUsername().equals(username)) return fail("Username mismatch.");
        if (!user.getFullName().equals(fullName)) return fail("Full name mismatch.");
        if (!user.getEmail().equals(email)) return fail("Email mismatch.");

        return pass();
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

    /**
     * Delete test file before/after test run
     */
    private static void cleanTestFile() {
        File file = new File(TEST_USER_FILE_PATH);
        if (file.exists()) file.delete();
    }
}
