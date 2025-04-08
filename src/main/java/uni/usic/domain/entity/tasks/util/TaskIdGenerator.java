package uni.usic.domain.entity.tasks.util;

import uni.usic.domain.entity.tasks.maintasks.Task;
import uni.usic.infrastructure.repository.tasks.TaskFileRepository;

import java.util.List;

public class TaskIdGenerator {
    private static int currentId = 1;
    private static final String PREFIX = "TASK";
    private final String filePath;

    public TaskIdGenerator(String filePath) {
        this.filePath = filePath;
    }

    public static void initialise(List<Task> tasks) {
        int max = tasks.stream()
                .map(Task::getId)
                .map(id -> id.replace(PREFIX + "-", ""))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        currentId = max + 1;
    }

    public String generateId() {
        TaskFileRepository taskFileRepository = new TaskFileRepository(filePath);
        List<Task> existingTasks = taskFileRepository.loadTaskListFromFile();
        TaskIdGenerator.initialise(existingTasks);
        return PREFIX + "-" + currentId++;
    }
}
