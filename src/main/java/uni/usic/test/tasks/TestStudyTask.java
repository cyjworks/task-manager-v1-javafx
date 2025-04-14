package uni.usic.test.tasks;

import uni.usic.domain.entity.tasks.StudyTask;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

public class TestStudyTask {
    public static void main(String[] args) {
        System.out.println("=== Test for StudyTask class ===\n");

        int totalTests = 0, passedTests = 0;

        totalTests++;
        if (testCreateStudyTask()) passedTests++;

        totalTests++;
        if (testGettersAndSetters()) passedTests++;

        totalTests++;
        if (testExecuteStudyTask()) passedTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");
    }

    /**
     * Test if a StudyTask is created correctly with expected values.
     * @return test result
     */
    public static boolean testCreateStudyTask() {
        System.out.println("Running testCreateStudyTask()...");

        String ownerUsername = "testUser";
        String id = "TASK-STUDY-1";
        TaskType type = TaskType.STUDY;
        String title = "Read AI Paper";
        String description = "Study the latest LLM research";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
        TaskPriority priority = TaskPriority.HIGH;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 2;
        String subject = "AI";
        String studyType = "Reading";
        int estimateTime = 120;

        StudyTask task = new StudyTask(ownerUsername, id, type, title, description,
                startDate, endDate, priority, progress, reminderDaysBefore,
                subject, studyType, estimateTime);

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
     * Test all getter and setter methods of StudyTask.
     * @return test result
     */
    public static boolean testGettersAndSetters() {
        System.out.println("Running testGettersAndSetters()...");

        String ownerUsername = "testUser";
        String id = "TASK-STUDY-1";
        TaskType type = TaskType.STUDY;
        String title = "Read AI Paper";
        String description = "Study the latest LLM research";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
        TaskPriority priority = TaskPriority.HIGH;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 2;
        String subject = "AI";
        String studyType = "Reading";
        int estimateTime = 120;

        StudyTask task = new StudyTask(ownerUsername, id, type, title, description,
                startDate, endDate, priority, progress, reminderDaysBefore,
                subject, studyType, estimateTime);

        task.setSubject("Math");
        task.setStudyType("Reading");
        task.setEstimateTime(90);

        if (!task.getSubject().equals("Math")) return fail("Subject setter/getter failed.");
        if (!task.getStudyType().equals("Reading")) return fail("StudyType setter/getter failed.");
        if (task.getEstimateTime() != 90) return fail("EstimateTime setter/getter failed.");

        return pass();
    }

    /**
     * Test execute() method of StudyTask.
     * @return test result
     */
    public static boolean testExecuteStudyTask() {
        System.out.println("Running testExecuteStudyTask()...");

        StudyTask task = new StudyTask(
                "user", "TASK-STUDY-2", TaskType.STUDY,
                "Watch JavaFX Lecture", "Watch the entire JavaFX UI building tutorial",
                LocalDate.now(), LocalDate.now().plusDays(2),
                TaskPriority.MEDIUM, TaskProgress.IN_PROGRESS, 1,
                "Computer Science", "Watching", 90
        );

        // For manual test, just call the method and assume no exception is thrown
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
