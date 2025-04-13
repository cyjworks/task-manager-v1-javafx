package uni.usic.test.tasks;

import uni.usic.application.service.tasks.TaskService;
import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;
import java.util.List;

public class TestTaskService {
    private static final String TEST_TASKS_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/test_tasks.txt";
    private static final String ownerUsername = "test";
    private static final TaskService taskService = new TaskService(TEST_TASKS_FILE_PATH);

    public static void main(String[] args) {
        System.out.println("=== Test for TaskService class ===\n");

        int totalTests = 0, passedTests = 0;

        totalTests++;
        if (testCreateTask()) passedTests++;

        totalTests++;
        if (testModifyTask()) passedTests++;

        totalTests++;
        if (testUpdateProgress()) passedTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");
    }

    /**
     * Test creating a task.
     * @return test result
     */
    public static boolean testCreateTask() {
        System.out.println("Running testCreateTask()...");

        TaskType type = TaskType.STUDY;
        String title = "Study Spring";
        String description = "Learn dependency injection";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(2);
        TaskPriority priority = TaskPriority.HIGH;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 2;

        Task task = taskService.createTask(ownerUsername, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);

        if (task == null) return fail("Task creation failed.");
        if (!task.getTitle().equals(title)) return fail("Title mismatch.");
        if (!task.getProgress().equals(progress)) return fail("Progress mismatch.");

        return pass();
    }

    /**
     * Test modifying an existing task.
     * @return test result
     */
    public static boolean testModifyTask() {
        System.out.println("Running testModifyTask()...");

        // Create original task
        TaskType type = TaskType.HABIT;
        String originalTitle = "Drink Water";
        String originalDescription = "Hydration tracking";
        LocalDate originalStart = LocalDate.now();
        LocalDate originalEnd = originalStart.plusDays(5);
        TaskPriority originalPriority = TaskPriority.MEDIUM;
        TaskProgress originalProgress = TaskProgress.TO_DO;
        Integer originalReminder = 1;

        Task task = taskService.createTask(ownerUsername, type, originalTitle, originalDescription, originalStart, originalEnd, originalPriority, originalProgress, originalReminder);

        // Modify task
        String newTitle = "Drink More Water";
        String newDescription = "Track 8 cups";
        LocalDate newStart = LocalDate.now();
        LocalDate newEnd = newStart.plusDays(7);
        TaskPriority newPriority = TaskPriority.HIGH;
        TaskProgress newProgress = TaskProgress.IN_PROGRESS;
        Integer newReminder = 2;

        Task modified = taskService.modifyTask(ownerUsername, task, task.getId(), newTitle, newDescription, newStart, newEnd, newPriority, newProgress, newReminder);

        if (modified == null) return fail("Modification failed.");
        if (!modified.getTitle().equals(newTitle)) return fail("Title not updated.");
        if (!modified.getProgress().equals(newProgress)) return fail("Progress not updated.");

        return pass();
    }

    /**
     * Test updating progress of an existing task.
     * @return test result
     */
    public static boolean testUpdateProgress() {
        System.out.println("Running testUpdateProgress()...");

        // Create task
        TaskType type = TaskType.WORK;
        String title = "Write Report";
        String description = "Finish quarterly summary";
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);
        TaskPriority priority = TaskPriority.HIGH;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminder = 1;

        Task task = taskService.createTask(ownerUsername, type, title, description, start, end, priority, progress, reminder);

        // Update progress
        TaskProgress newProgress = TaskProgress.DONE;
        Task updated = taskService.updateProgress(ownerUsername, task, task.getId(), newProgress);

        if (updated == null) return fail("Update failed.");
        if (!updated.getProgress().equals(newProgress)) return fail("Progress was not updated correctly.");

        return pass();
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
