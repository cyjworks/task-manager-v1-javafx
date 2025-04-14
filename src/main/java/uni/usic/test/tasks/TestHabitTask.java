package uni.usic.test.tasks;

import uni.usic.domain.entity.tasks.HabitTask;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

public class TestHabitTask {
    public static void main(String[] args) {
        System.out.println("=== Test for HabitTask class ===\n");

        int totalTests = 0, passedTests = 0;

        totalTests++;
        if (testCreateHabitTask()) passedTests++;

        totalTests++;
        if (testGettersAndSetters()) passedTests++;

        totalTests++;
        if (testExecuteHabitTask()) passedTests++;

        totalTests++;
        if (testStreakMethods()) passedTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");
    }

    /**
     * Test if a HabitTask is created correctly with expected values.
     * @return test result
     */
    public static boolean testCreateHabitTask() {
        System.out.println("Running testCreateHabitTask()...");

        String ownerUsername = "testUser";
        String id = "TASK-H1";
        TaskType type = TaskType.HABIT;
        String title = "Drink Water";
        String description = "Drink 8 cups of water";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);
        TaskPriority priority = TaskPriority.LOW;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 3;
        int streak = 0;
        String frequency = "Daily";

        HabitTask task = new HabitTask(ownerUsername, id, type, title, description,
                startDate, endDate, priority, progress, reminderDaysBefore, streak, frequency);

        if (!task.getId().equals(id)) return fail("ID does not match");
        if (!task.getTitle().equals(title)) return fail("Title does not match");
        if (!task.getFrequency().equals(frequency)) return fail("Frequency does not match");
        if (task.getStreak() != streak) return fail("Initial streak should be 0");

        return pass();
    }

    /**
     * Test getter and setter methods for HabitTask.
     * @return test result
     */
    public static boolean testGettersAndSetters() {
        System.out.println("Running testGettersAndSetters()...");

        String ownerUsername = "user2";
        String id = "TASK-H2";
        TaskType type = TaskType.HABIT;
        String title = "Stretching";
        String description = "Stretch for 10 minutes";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(2);
        TaskPriority priority = TaskPriority.MEDIUM;
        TaskProgress progress = TaskProgress.IN_PROGRESS;
        Integer reminderDaysBefore = 1;
        int streak = 5;
        String frequency = "Weekly";

        HabitTask task = new HabitTask(ownerUsername, id, type, title, description,
                startDate, endDate, priority, progress, reminderDaysBefore, streak, frequency);

        task.setStreak(10);
        task.setFrequency("Daily");

        if (task.getStreak() != 10) return fail("Streak setter/getter failed.");
        if (!task.getFrequency().equals("Daily")) return fail("Frequency setter/getter failed.");

        return pass();
    }

    /**
     * Test execute() method of HabitTask.
     * @return test result
     */
    public static boolean testExecuteHabitTask() {
        System.out.println("Running testExecuteHabitTask()...");

        String ownerUsername = "user";
        String id = "TASK-H3";
        TaskType type = TaskType.HABIT;
        String title = "Meditation";
        String description = "10 minutes mindfulness";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        TaskPriority priority = TaskPriority.HIGH;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 2;
        int streak = 3;
        String frequency = "Daily";

        HabitTask task = new HabitTask(ownerUsername, id, type, title, description,
                startDate, endDate, priority, progress, reminderDaysBefore, streak, frequency);

        try {
            task.execute(); // prints message & increments streak
        } catch (Exception e) {
            return fail("execute() method threw an exception: " + e.getMessage());
        }

        return pass();
    }

    /**
     * Test incrementStreak() and resetStreak() methods.
     * @return test result
     */
    public static boolean testStreakMethods() {
        System.out.println("Running testStreakMethods()...");

        String ownerUsername = "user";
        String id = "TASK-H4";
        TaskType type = TaskType.HABIT;
        String title = "Exercise";
        String description = "Workout for 30 mins";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
        TaskPriority priority = TaskPriority.MEDIUM;
        TaskProgress progress = TaskProgress.IN_PROGRESS;
        Integer reminderDaysBefore = 1;
        int streak = 0;
        String frequency = "Daily";

        HabitTask task = new HabitTask(ownerUsername, id, type, title, description,
                startDate, endDate, priority, progress, reminderDaysBefore, streak, frequency);

        task.incrementStreak();
        if (task.getStreak() != 1) return fail("Streak should be 1 after increment");

        task.resetStreak();
        if (task.getStreak() != 0) return fail("Streak should be 0 after reset");

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
