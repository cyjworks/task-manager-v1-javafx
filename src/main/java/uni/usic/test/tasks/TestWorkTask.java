package uni.usic.test.tasks;

import uni.usic.domain.entity.tasks.WorkTask;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

public class TestWorkTask {
    public static void main(String[] args) {
        System.out.println("=== Test for WorkTask class ===\n");

        int totalTests = 0, passedTests = 0;

        totalTests++;
        if (testCreateWorkTask()) passedTests++;

        totalTests++;
        if (testGettersAndSetters()) passedTests++;

        totalTests++;
        if (testExecuteWorkTask()) passedTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");
    }

    /**
     * Test if a WorkTask is created correctly with expected values.
     * @return test result
     */
    public static boolean testCreateWorkTask() {
        System.out.println("Running testCreateWorkTask()...");

        String ownerUsername = "testUser";
        String id = "TASK-WORK-1";
        TaskType type = TaskType.WORK;
        String title = "Team Presentation";
        String description = "Prepare slides for client";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(4);
        TaskPriority priority = TaskPriority.MEDIUM;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 2;
        String careerTask = "Presentation & Pitching";

        WorkTask task = new WorkTask(ownerUsername, id, type, title, description,
                startDate, endDate, priority, progress, reminderDaysBefore, careerTask);

        if (!task.getId().equals(id)) return fail("ID does not match");
        if (!task.getTitle().equals(title)) return fail("Title does not match");
        if (!task.getDescription().equals(description)) return fail("Description does not match");
        if (!task.getStartDate().equals(startDate)) return fail("Start date does not match");
        if (!task.getEndDate().equals(endDate)) return fail("End date does not match");
        if (!task.getPriority().equals(priority)) return fail("Priority does not match");
        if (!task.getProgress().equals(progress)) return fail("Progress does not match");
        if (!task.getReminderDaysBefore().equals(reminderDaysBefore)) return fail("Reminder days before does not match");

        return pass();
    }

    /**
     * Test getter and setter methods for WorkTask.
     * @return test result
     */
    public static boolean testGettersAndSetters() {
        System.out.println("Running testGettersAndSetters()...");

        String ownerUsername = "testUser";
        String id = "TASK-WORK-1";
        TaskType type = TaskType.WORK;
        String title = "Team Presentation";
        String description = "Prepare slides for client";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(4);
        TaskPriority priority = TaskPriority.MEDIUM;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 2;
        String careerTask = "Presentation & Pitching";

        WorkTask task = new WorkTask(ownerUsername, id, type, title, description,
                startDate, endDate, priority, progress, reminderDaysBefore, careerTask);

        task.setTitle("Updated Title");
        task.setCareerTask("Backend Refactoring");

        if (!task.getTitle().equals("Updated Title")) return fail("Title setter/getter failed.");
        if (!task.getCareerTask().equals("Backend Refactoring")) return fail("CareerTask setter/getter failed.");

        return pass();
    }

    /**
     * Test execute() method of WorkTask.
     * @return test result
     */
    public static boolean testExecuteWorkTask() {
        System.out.println("Running testExecuteWorkTask()...");

        WorkTask task = new WorkTask(
                "user", "TASK-WORK-2", TaskType.WORK,
                "Update Resume", "Add recent project experience",
                LocalDate.now(), LocalDate.now().plusDays(2),
                TaskPriority.HIGH, TaskProgress.IN_PROGRESS, 1,
                "Job Application"
        );

        try {
            task.execute(); // should print info to console
        } catch (Exception e) {
            return fail("execute() method threw an exception: " + e.getMessage());
        }

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
