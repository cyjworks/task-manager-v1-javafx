package uni.usic.test.users;

import uni.usic.application.service.users.UserService;
import uni.usic.domain.entity.users.User;
import uni.usic.infrastructure.repository.users.UserFileRepository;

import java.io.File;
import java.io.IOException;

public class TestUserService {
    private static final String TEST_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/test_users.txt";
    private static final UserFileRepository userRepository = new UserFileRepository(TEST_FILE_PATH);
    private static final UserService userService = new UserService(userRepository);

    public static void main(String[] args) throws IOException {
        System.out.println("=== Test for UserService class ===\n");

        cleanTestFile();

        int totalTests = 0, passedTests = 0;

        if (runTest("SignUp Success", testSignUpSuccess())) passedTests++;
        totalTests++;

        if (runTest("SignUp Duplicate", testSignUpDuplicate())) passedTests++;
        totalTests++;

        if (runTest("SignIn Success", testSignInSuccess())) passedTests++;
        totalTests++;

        if (runTest("SignIn Wrong Password", testSignInWrongPassword())) passedTests++;
        totalTests++;

        if (runTest("Find By Username", testFindByUsername())) passedTests++;
        totalTests++;

        if (runTest("Find By Username (Not Found)", testFindByUsernameNotFound())) passedTests++;
        totalTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");

        cleanTestFile();
    }

    private static boolean testSignUpSuccess() {
        User user = userService.signUp("newuser", "pass", "New User", "new@a.com");
        return user != null && user.getUsername().equals("newuser");
    }

    private static boolean testSignUpDuplicate() {
        userService.signUp("dupuser", "pass", "Dup User", "dup@a.com");
        User duplicate = userService.signUp("dupuser", "pass", "Dup Again", "dup@a.com");
        return duplicate == null;
    }

    private static boolean testSignInSuccess() {
        userService.signUp("signin", "1234", "Sign In", "signin@test.com");
        return userService.signIn("signin", "1234");
    }

    private static boolean testSignInWrongPassword() {
        userService.signUp("wrongpass", "right", "Wrong", "wp@a.com");
        return !userService.signIn("wrongpass", "wrong");
    }

    private static boolean testFindByUsername() {
        userService.signUp("findme", "pass", "Finder", "find@me.com");
        User found = userService.findByUsername("findme");
        return found != null && found.getFullName().equals("Finder");
    }

    private static boolean testFindByUsernameNotFound() {
        User notFound = userService.findByUsername("ghostuser");
        return notFound == null;
    }

    private static boolean runTest(String name, boolean result) {
        System.out.println("Running: " + name);
        if (result) {
            System.out.println("Test Passed!\n");
        } else {
            System.out.println("Test Failed!\n");
        }
        return result;
    }

    private static void cleanTestFile() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();
    }
}
