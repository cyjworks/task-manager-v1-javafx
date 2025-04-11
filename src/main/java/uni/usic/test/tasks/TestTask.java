package uni.usic.test.tasks;

import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;

import java.time.LocalDate;

public class TestTask {
    public static void main(String[] args) {
        System.out.println("=== Test for Task class ===\n");

        int totalTests = 0, passedTests = 0;

        // Run all tests
        totalTests++;
        if(testCreateTask()) {
            passedTests++;
        }

        totalTests++;
        if(testSetTaskReminder()) {
            passedTests++;
        }

        totalTests++;
        if(testIsReminderDue()) {
            passedTests++;
        }

        System.out.println("\n=> Test Summary: " + passedTests + "/" + totalTests + " tests passed.");
    }

    /**
     * Test if a Task is created successfully with correct values.
     * @return
     */
    public static boolean testCreateTask() {
        System.out.println("Running testCreateTask()...");

        String id = "TEST-1";
        TaskType type = TaskType.STUDY;
        String title = "Study Java";
        String description = "Complete inheritance tutorial";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
        TaskPriority priority = TaskPriority.HIGH;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 3;

        Task task = new Task(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);

        if(!task.getTitle().equals(title)) return fail("Title is incorrect.");
        if(!task.getDescription().equals(description)) return fail("Description is incorrect.");
        if(!task.getStartDate().equals(startDate)) return fail("Start date is incorrect.");
        if(!task.getEndDate().equals(endDate)) return fail("End date is incorrect.");
        if(!task.getPriority().equals(priority)) return fail("Priority is incorrect.");
        if(task.getProgress() != TaskProgress.TO_DO) return fail("Progress should be TO_DO initially.");
        if(task.getReminderDaysBefore() != null) return fail("Reminder days before should be null initially.");

        return pass();
    }

    /**
     * Test if the reminder is correctly set.
     * @return
     */
    public static boolean testSetTaskReminder() {
        System.out.println("Running testSetTaskReminder()...");

        String id = "TEST-1";
        TaskType type = TaskType.WORK;
        String title = "Prepare Report";
        String description = "Write quarterly report";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);
        TaskPriority priority = TaskPriority.MEDIUM;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 3;

        Task task = new Task(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);

        task.setTaskReminder(2);
        if(!task.getReminderDaysBefore().equals(2)) return fail("Reminder days before was not set correctly.");

        return pass();
    }

    public static boolean testIsReminderDue() {
        System.out.println("Running testIsReminderDue()...");

        String id = "TEST-1";
        TaskType type = TaskType.HABIT;
        String title = "Workout";
        String description = "Exercise for 30 mins";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(2);
        TaskPriority priority = TaskPriority.LOW;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 3;

        Task task = new Task(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);

        task.setTaskReminder(2);

        if(!task.isReminderDue(2)) return fail("Reminder should be triggered today.");

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
