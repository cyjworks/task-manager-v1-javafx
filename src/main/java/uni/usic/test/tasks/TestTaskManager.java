package uni.usic.test.tasks;

import uni.usic.application.service.tasks.TaskManager;
import uni.usic.application.service.tasks.TaskService;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.infrastructure.repository.tasks.TaskFileRepository;

import java.time.LocalDate;
import java.util.List;

public class TestTaskManager {
    // TODO: generate test data beforehand
    private static final String ownerUsername = "test";
    private static final String TEST_TASKS_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/test_tasks.txt";
    private static final TaskFileRepository taskFileRepository = new TaskFileRepository(TEST_TASKS_FILE_PATH);
    private static final TaskService taskService = new TaskService(TEST_TASKS_FILE_PATH);
    private static final TaskManager taskManager = new TaskManager(ownerUsername, taskService, taskFileRepository);

    public static void main(String[] args) {
        System.out.println("=== Test for TaskManager class ===\n");
        int totalTests = 0, passedTests = 0;

        totalTests++;
        if(testCreateTask()) passedTests++;

        totalTests++;
        if(testViewTaskList()) passedTests++;

        totalTests++;
        if(testViewTaskById()) passedTests++;

        totalTests++;
        if (testGetUniqueTaskTypes()) passedTests++;

        totalTests++;
        if(testModifyTask()) passedTests++;

        totalTests++;
        if(testCheckIfTaskExists()) passedTests++;

        totalTests++;
        if(testUpdateProgress()) passedTests++;

        totalTests++;
        if(testDeleteTask()) passedTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");
    }

    /**
     * Tests whether the task list can be loaded without returning null.
     * @return test result
     */
    public static boolean testViewTaskList() {
        System.out.println("Running testViewTaskList()...");

        List<Task> taskMap = taskManager.viewTaskList();
        if(taskMap == null) return fail("Task list should not be null.");
        return pass();
    }

    /**
     * Tests whether a task can be retrieved by ID.
     * @return test result
     */
    public static boolean testViewTaskById() {
        System.out.println("Running testViewTaskById()...");

        String id = "TASK-1";
        Task task = taskManager.viewTaskById(id);
        if (!task.getId().equals(id)) return fail("Task with ID " + id + " should exist.");
        return pass();
    }

    /**
     * Tests whether unique task types are returned from the task list.
     * @return test result
     */
    public static boolean testGetUniqueTaskTypes() {
        System.out.println("Running testGetUniqueTaskTypes()...");

        List<Task> taskList = taskManager.viewTaskList();
        if (taskList.isEmpty()) return fail("No tasks available to test TaskType extraction.");

        if (taskManager.getUniqueTaskTypes().isEmpty()) return fail("Returned TaskType set should not be empty.");

        return pass();
    }

    /**
     * Tests whether a new task can be created successfully.
     * @return test result
     */
    public static boolean testCreateTask() {
        System.out.println("Running testCreateTask()...");

        TaskType type = TaskType.STUDY;
        String title = "Study Java";
        String description = "Complete tutorial";
//        LocalDate startDate = LocalDate.of(2025, 4, 1);
//        LocalDate endDate = LocalDate.of(2025, 4, 5);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(3);
        TaskPriority priority = TaskPriority.HIGH;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 3;
        Task task = taskManager.createTask(type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        if (task == null) return fail("Failed to create task.");
        return pass();
    }

    /**
     * Tests whether an existing task can be modified.
     * @return test result
     */
    public static boolean testModifyTask() {
        System.out.println("Running testModifyTask()...");

        String id = "TASK-1";
        String title = "Study Java";
        String description = "Complete tutorial";
        LocalDate startDate = LocalDate.of(2025, 4, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 5);
        TaskPriority priority = TaskPriority.HIGH;
        TaskProgress progress = TaskProgress.IN_PROGRESS;
        int reminderDaysBefore = 3;
        Task task = taskManager.modifyTask(id, title, description, startDate, endDate, priority, progress, reminderDaysBefore);

        // TODO: consider assertEqual()
        if (task == null) return fail("Task is null");
        if (!task.getId().equals(id)) return fail("ID does not match");
        if (!task.getTitle().equals(title)) return fail("Title does not match");
        if (!task.getDescription().equals(description)) return fail("Description does not match");
        if (!task.getStartDate().equals(startDate)) return fail("Start date does not match");
        if (!task.getEndDate().equals(endDate)) return fail("End date does not match");
        if (!task.getPriority().equals(priority)) return fail("Priority does not match");
        if (!task.getProgress().equals(progress)) return fail("Progress does not match");
        if (task.getReminderDaysBefore() == null || task.getReminderDaysBefore() != reminderDaysBefore)
            return fail("Reminder days before does not match");
        return pass();
    }

    /**
     * Tests whether task existence can be checked by ID.
     * @return test result
     */
    public static boolean testCheckIfTaskExists() {
        System.out.println("Running testCheckIfTaskExists()...");

        String id = "TASK-1";
        Task task = taskManager.viewTaskById(id);
        if (task == null) return fail("Expected task with ID " + id + " to exist, but was not found.");
        return pass();
    }

    /**
     * Tests whether a task's progress can be updated.
     * @return test result
     */
    public static boolean testUpdateProgress() {
        System.out.println("Running testUpdateProgress()...");

        String id = "TASK-1";
        TaskProgress progress = TaskProgress.DONE;
        Task task = taskManager.updateProgress(id, progress);
        if (!task.getProgress().equals(progress)) return fail("Progress does not match");

        return pass();
    }

    /**
     * Tests whether a task can be deleted by ID.
     * @return test result
     */
    public static boolean testDeleteTask() {
        System.out.println("Running testDeleteTask()...");

        String id = "TASK-1";
        Task task = taskManager.viewTaskById(id);
        if (task == null) return fail("Task with ID " + id + " should exist before deletion.");

        boolean deleted = taskManager.deleteTask(id);
        if (!deleted) return fail("Failed to delete task with ID " + id + ".");

        Task afterDelete = taskManager.viewTaskById(id);
        if (afterDelete != null) return fail("Task with ID " + id + " still exists after deletion.");

        return pass();
    }

    /**
     * Helper method to print success message.
     * @return true
     */
    public static boolean pass() {
        System.out.println("Test Passed!\n");
        return true;
    }

    /**
     * Helper method to print failure message.
     * @param message failure reason
     * @return false
     */
    public static boolean fail(String message) {
        System.out.println("Test Failed! Reason: " + message + "\n");
        return false;
    }
}
