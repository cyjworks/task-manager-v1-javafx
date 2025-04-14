package uni.usic.test.tasks;

import uni.usic.domain.entity.tasks.GoalTask;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

public class TestGoalTask {
    public static void main(String[] args) {
        System.out.println("=== Test for GoalTask class ===\n");

        int totalTests = 0, passedTests = 0;

        totalTests++;
        if (testCreateGoalTask()) passedTests++;

        totalTests++;
        if (testGettersAndSetters()) passedTests++;

        totalTests++;
        if (testExecuteGoalTask()) passedTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");
    }

    /**
     * Test if a GoalTask is created correctly with expected values.
     * @return test result
     */
    public static boolean testCreateGoalTask() {
        System.out.println("Running testCreateGoalTask()...");

        String ownerUsername = "goalUser";
        String id = "TASK-G1";
        TaskType type = TaskType.GOAL;
        String title = "Lose Weight";
        String description = "Lose 5kg in 2 months";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(60);
        TaskPriority priority = TaskPriority.HIGH;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 5;
        String target = "5kg";
        int progressPercentage = 0;

        GoalTask task = new GoalTask(ownerUsername, id, type, title, description,
                startDate, endDate, priority, progress, reminderDaysBefore, target, progressPercentage);

        if (!task.getId().equals(id)) return fail("ID does not match");
        if (!task.getTarget().equals(target)) return fail("Target does not match");
        if (task.getProgressPercentage() != progressPercentage) return fail("Progress percentage does not match");

        return pass();
    }

    /**
     * Test all getter and setter methods of GoalTask.
     * @return test result
     */
    public static boolean testGettersAndSetters() {
        System.out.println("Running testGettersAndSetters()...");

        String ownerUsername = "goalUser2";
        String id = "TASK-G2";
        TaskType type = TaskType.GOAL;
        String title = "Learn Japanese";
        String description = "Reach JLPT N3";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(6);
        TaskPriority priority = TaskPriority.MEDIUM;
        TaskProgress progress = TaskProgress.IN_PROGRESS;
        Integer reminderDaysBefore = 7;
        String target = "JLPT N3";
        int progressPercentage = 10;

        GoalTask task = new GoalTask(ownerUsername, id, type, title, description,
                startDate, endDate, priority, progress, reminderDaysBefore, target, progressPercentage);

        task.setTarget("JLPT N2");
        task.setProgressPercentage(40);

        if (!task.getTarget().equals("JLPT N2")) return fail("Target setter/getter failed.");
        if (task.getProgressPercentage() != 40) return fail("Progress percentage setter/getter failed.");

        return pass();
    }

    /**
     * Test execute() method of GoalTask.
     * @return test result
     */
    public static boolean testExecuteGoalTask() {
        System.out.println("Running testExecuteGoalTask()...");

        String ownerUsername = "goalUser3";
        String id = "TASK-G3";
        TaskType type = TaskType.GOAL;
        String title = "Build Portfolio";
        String description = "Complete 5 personal projects";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(3);
        TaskPriority priority = TaskPriority.HIGH;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 3;
        String target = "5 Projects";
        int progressPercentage = 20;

        GoalTask task = new GoalTask(ownerUsername, id, type, title, description,
                startDate, endDate, priority, progress, reminderDaysBefore, target, progressPercentage);

        try {
            task.execute(); // console print only
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
