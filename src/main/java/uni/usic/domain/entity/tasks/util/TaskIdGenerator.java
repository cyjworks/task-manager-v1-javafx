package uni.usic.domain.entity.tasks.util;

import uni.usic.domain.entity.tasks.Task;
import uni.usic.infrastructure.repository.tasks.TaskFileRepository;

import java.util.List;

/**
 * Generates unique task IDs based on existing tasks for each user.
 */
public class TaskIdGenerator {
    private static int currentId = 1;
    private static final String PREFIX = "TASK";
    private final String filePath;

    /**
     * Constructs a TaskIdGenerator with the given file path.
     *
     * @param filePath path to the task file
     */
    public TaskIdGenerator(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Sets the initial task ID value based on existing tasks.
     *
     * @param tasks list of existing tasks
     */
    public static void initialise(List<Task> tasks) {
        int max = tasks.stream()
                .map(Task::getId)
                .map(id -> id.replace(PREFIX + "-", ""))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        currentId = max + 1;
    }

    /**
     * Generates a new unique task ID for the given user.
     *
     * @param ownerUsername the task owner
     * @return generated task ID
     */
    public String generateId(String ownerUsername) {
        TaskFileRepository taskFileRepository = new TaskFileRepository(filePath);
        List<Task> existingTasks = taskFileRepository.loadTaskListFromFile(ownerUsername);
        TaskIdGenerator.initialise(existingTasks);
        return PREFIX + "-" + currentId++;
    }
}
