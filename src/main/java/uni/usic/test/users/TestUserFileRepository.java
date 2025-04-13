package uni.usic.test.users;

import uni.usic.domain.entity.users.User;
import uni.usic.infrastructure.repository.users.UserFileRepository;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class TestUserFileRepository {
    private static final String TEST_USER_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/test_users.txt";
    private static final UserFileRepository userRepository = new UserFileRepository(TEST_USER_FILE_PATH);

    public static void main(String[] args) {
        System.out.println("=== Test for UserFileRepository class ===\n");

        cleanTestFile();

        int totalTests = 0, passedTests = 0;

        totalTests++;
        if (testSaveUser()) passedTests++;

        totalTests++;
        if (testFindAll()) passedTests++;

        totalTests++;
        if (testFindByUsername()) passedTests++;

        totalTests++;
        if (testExistsByUsername()) passedTests++;

        totalTests++;
        if (testUpdateUser()) passedTests++;

        totalTests++;
        if (testDeleteByUsername()) passedTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");

        cleanTestFile();
    }

    public static boolean testSaveUser() {
        System.out.println("Running testSaveUser()...");
        User user = new User("testuser", "1234", "Test User", "test@example.com");
        boolean result = userRepository.save(user);
        return result ? pass() : fail("Failed to save user.");
    }

    public static boolean testFindAll() {
        System.out.println("Running testFindAll()...");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) return fail("User list should not be empty.");
        return pass();
    }

    public static boolean testFindByUsername() {
        System.out.println("Running testFindByUsername()...");
        Optional<User> userOpt = userRepository.findByUsername("testuser");
        if (userOpt.isEmpty()) return fail("User 'testuser' should exist.");
        return pass();
    }

    public static boolean testExistsByUsername() {
        System.out.println("Running testExistsByUsername()...");
        boolean exists = userRepository.existsByUsername("testuser");
        return exists ? pass() : fail("User 'testuser' should exist.");
    }

    public static boolean testUpdateUser() {
        System.out.println("Running testUpdateUser()...");
        User updatedUser = new User("testuser", "newpass", "Test User", "updated@example.com");
        boolean result = userRepository.update(updatedUser);
        if (!result) return fail("Failed to update user.");

        Optional<User> userOpt = userRepository.findByUsername("testuser");
        return userOpt.map(user ->
                user.getPassword().equals("newpass") &&
                        user.getEmail().equals("updated@example.com")
        ).orElse(false) ? pass() : fail("Updated values do not match.");
    }

    public static boolean testDeleteByUsername() {
        System.out.println("Running testDeleteByUsername()...");
        boolean deleted = userRepository.deleteByUsername("testuser");
        if (!deleted) return fail("User 'testuser' should have been deleted.");

        Optional<User> userOpt = userRepository.findByUsername("testuser");
        return userOpt.isEmpty() ? pass() : fail("User 'testuser' still exists after deletion.");
    }

    public static boolean pass() {
        System.out.println("Test Passed!\n");
        return true;
    }

    public static boolean fail(String message) {
        System.out.println("Test Failed! Reason: " + message + "\n");
        return false;
    }

    private static void cleanTestFile() {
        File file = new File(TEST_USER_FILE_PATH);
        if (file.exists()) file.delete();
    }
}
