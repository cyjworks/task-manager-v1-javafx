package uni.usic.test.tasks;

import uni.usic.domain.entity.tasks.StudyTask;
import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.infrastructure.repository.tasks.TaskFileRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TestTaskFileRepository {
    private static final String TEST_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/test_tasks.txt";
    private static final String OWNER = "testUser";
    private static final TaskFileRepository repository = new TaskFileRepository(TEST_FILE_PATH);

    public static void main(String[] args) {
        System.out.println("=== Test for TaskFileRepository class ===\n");

        int totalTests = 0, passedTests = 0;

        repository.deleteAll(OWNER);    // reset

        totalTests++;
        if (testSave()) passedTests++;

        totalTests++;
        if (testFindAll()) passedTests++;

        totalTests++;
        if (testFindById()) passedTests++;

        totalTests++;
        if (testUpdate()) passedTests++;

        totalTests++;
        if (testDeleteById()) passedTests++;

        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");
    }

    /**
     * Test saving a task.
     * @return test result
     */
    public static boolean testSave() {
        System.out.println("Running testSave()...");

        String id = "TASK-999";
        String title = "Unit Test Save";
        String description = "Testing save method only";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        TaskPriority priority = TaskPriority.HIGH;
        TaskProgress progress = TaskProgress.TO_DO;
        Integer reminderDaysBefore = 2;
        String subject = "Math";
        String studyType = "Practice";
        int estimateTime = 60;

        Task task = new StudyTask(
                OWNER, id, TaskType.STUDY,
                title, description, startDate, endDate,
                priority, progress, reminderDaysBefore,
                subject, studyType, estimateTime
        );

        boolean saved = repository.save(task);
        if (!saved) return fail("Task was not saved properly.");

        return pass();
    }

    /**
     * Test retrieving all tasks for a user.
     * @return test result
     */
    public static boolean testFindAll() {
        System.out.println("Running testFindAll()...");

        List<Task> tasks = repository.findAll(OWNER);
        if (tasks.isEmpty()) return fail("Task list should not be empty.");

        return pass();
    }

    /**
     * Test retrieving a task by ID.
     * @return test result
     */
    public static boolean testFindById() {
        System.out.println("Running testFindById()...");

        String id = "TASK-999";
        Optional<Task> found = repository.findById(OWNER, id);
        if (found.isEmpty()) return fail("Task was not found.");
        if (!found.get().getId().equals(id)) return fail("Task ID mismatch.");

        return pass();
    }


    /**
     * Test updating an existing task.
     * @return test result
     */
    public static boolean testUpdate() {
        System.out.println("Running testUpdate()...");

        String id = "TASK-999";
        Optional<Task> opt = repository.findById(OWNER, id);
        if (opt.isEmpty()) return fail("Task not found for update test.");

        Task task = opt.get();
        task.setTitle("Updated Title");
        task.setProgress(TaskProgress.DONE);

        boolean updated = repository.update(OWNER, task);
        if (!updated) return fail("Task was not updated.");

        Optional<Task> afterUpdate = repository.findById(OWNER, id);
        if (afterUpdate.isEmpty() || !afterUpdate.get().getTitle().equals("Updated Title"))
            return fail("Updated values not saved correctly.");

        return pass();
    }

    /**
     * Test deleting a task by ID.
     * @return test result
     */
    public static boolean testDeleteById() {
        System.out.println("Running testDeleteById()...");

        String id = "TASK-999";

        boolean deleted = repository.deleteById(OWNER, id);
        if (!deleted) return fail("Task was not deleted.");

        Optional<Task> afterDelete = repository.findById(OWNER, id);
        if (afterDelete.isPresent()) return fail("Task still exists after deletion.");

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
