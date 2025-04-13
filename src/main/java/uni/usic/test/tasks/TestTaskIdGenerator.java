package uni.usic.test.tasks;

import uni.usic.domain.entity.tasks.util.TaskIdGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class TestTaskIdGenerator {
    private static final String TEST_TASKS_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/test_tasks.txt";

    public static void main(String[] args) {
        System.out.println("=== Test for TaskIdGenerator class ===\n");

        int totalTests = 0, passedTests = 0;

        cleanTestFile();
        setupSampleTasks();

        totalTests++;
        if (testInitialiseAndGenerateId()) passedTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");
    }

    /**
     * Tests initialise() and generateId() using test task data.
     * @return test result
     */
    public static boolean testInitialiseAndGenerateId() {
        System.out.println("Running testInitialiseAndGenerateId()...");

        String ownerUsername = "test";

        TaskIdGenerator generator = new TaskIdGenerator(TEST_TASKS_FILE_PATH);
        String generatedId = generator.generateId(ownerUsername);

        if (!generatedId.equals("TASK-3")) return fail("Expected TASK-3 but got " + generatedId);

        return pass();
    }

    /**
     * Creates sample task file with 2 existing tasks.
     */
    private static void setupSampleTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_TASKS_FILE_PATH))) {
            writer.write("test|TASK-1|STUDY|Math Homework|Do exercises|2025-04-01|2025-04-03|HIGH|TO_DO|2|Math|Reading|60");
            writer.newLine();
            writer.write("test|TASK-2|STUDY|Science Project|Build model|2025-04-02|2025-04-05|MEDIUM|TO_DO|1|Science|Building|120");
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Failed to set up test data: " + e.getMessage());
        }
    }

    /**
     * Deletes the test file if it exists.
     */
    private static void cleanTestFile() {
        File file = new File(TEST_TASKS_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Prints success message.
     * @return true
     */
    public static boolean pass() {
        System.out.println("Test Passed!\n");
        return true;
    }

    /**
     * Prints failure message.
     * @param message failure reason
     * @return false
     */
    public static boolean fail(String message) {
        System.out.println("Test Failed! Reason: " + message + "\n");
        return false;
    }
}
