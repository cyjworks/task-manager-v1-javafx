package uni.usic.test.tasks;

import uni.usic.application.service.tasks.TaskService;
import uni.usic.domain.entity.tasks.Task;

import java.util.List;

public class TestTaskService {
    private static final String TEST_TASKS_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/test_tasks.txt";

    public static void main(String[] args) {


        System.out.println("=== Test for TaskManger class ===\n");

        int totalTests = 0, passedTests = 0;

        // Run all tests
        totalTests++;
//        if(testAddTask()) {
//            passedTests++;
//        }

        totalTests++;
        if(testViewTaskList()) {
            passedTests++;
        }

        totalTests++;
        if(testGetTaskById()) {
            passedTests++;
        }

        totalTests++;
        if(testRemoveTask()) {
            passedTests++;
        }

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");
    }

//    public static boolean testAddTask() {
//        TaskService taskService = new TaskService();
//        System.out.println("Running testAddTask()...");
//
//        Task task = new Task("Study Java", "Complete tutorial", LocalDate.now(), LocalDate.now().plusDays(3), TaskPriority.HIGH);
//        boolean added = taskService.addTask(task);
//
//        if (!added) return fail("Task was not added successfully.");
//        return pass();
//    }

    public static boolean testViewTaskList() {
        TaskService taskService = new TaskService(TEST_TASKS_FILE_PATH);
        System.out.println("Running testViewTaskList()...");

        String ownerUsername = "test";
        List<Task> tasks = taskService.viewTaskList(ownerUsername);
        if (tasks == null) return fail("Task list should not be null.");

        return pass();
    }

    public static boolean testGetTaskById() {
        TaskService taskService = new TaskService(TEST_TASKS_FILE_PATH);
        System.out.println("Running testGetTaskById()...");

        Task task = taskService.getTaskById("TASK-1");
        if (task == null) return fail("Task with ID TASK-1 should exist.");
        return pass();
    }

    public static boolean testRemoveTask() {
        TaskService taskService = new TaskService(TEST_TASKS_FILE_PATH);
        System.out.println("Running testRemoveTask()...");

        boolean removed = taskService.removeTask("TASK-1");
        if (!removed) return fail("Task was not removed successfully.");

        Task task = taskService.getTaskById("TASK-1");
        if (task != null) return fail("Task should not exist after removal.");
        return pass();
    }

    /**
     * Helper method to print success message.
     * @return
     */
    public static boolean pass() {
        System.out.println("Test Passed!");
        return true;
    }

    /**
     * Helper method to print failure message.
     * @param message
     * @return
     */
    public static boolean fail(String message) {
        System.out.println("Test Failed! Reason: " + message);
        return false;
    }
}
