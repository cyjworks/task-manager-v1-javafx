package uni.usic.test.users;

import uni.usic.domain.entity.users.User;

public class TestUser {
    public static void main(String[] args) {
        System.out.println("=== Test for User class ===\n");
        int totalTests = 0, passedTests = 0;

        totalTests++;
        if (testUserCreation()) passedTests++;

        totalTests++;
        if (testSettersAndGetters()) passedTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");
    }

    public static boolean testUserCreation() {
        System.out.println("Running testUserCreation()...");

        User user = new User("testUser", "pass123", "Test User", "test@example.com");

        if (!user.getUsername().equals("testUser")) return fail("Username not set correctly.");
        if (!user.getPassword().equals("pass123")) return fail("Password not set correctly.");
        if (!user.getFullName().equals("Test User")) return fail("Full name not set correctly.");
        if (!user.getEmail().equals("test@example.com")) return fail("Email not set correctly.");

        return pass();
    }

    public static boolean testSettersAndGetters() {
        System.out.println("Running testSettersAndGetters()...");

        User user = new User("a", "b", "c", "d");

        user.setUsername("newUser");
        user.setPassword("newPass");
        user.setFullName("New Name");
        user.setEmail("new@example.com");

        if (!user.getUsername().equals("newUser")) return fail("Username not updated correctly.");
        if (!user.getPassword().equals("newPass")) return fail("Password not updated correctly.");
        if (!user.getFullName().equals("New Name")) return fail("Full name not updated correctly.");
        if (!user.getEmail().equals("new@example.com")) return fail("Email not updated correctly.");

        return pass();
    }

    public static boolean pass() {
        System.out.println("Test Passed!\n");
        return true;
    }

    public static boolean fail(String message) {
        System.out.println("Test Failed! Reason: " + message + "\n");
        return false;
    }
}
