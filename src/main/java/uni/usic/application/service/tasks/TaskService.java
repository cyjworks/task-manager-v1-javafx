package uni.usic.application.service.tasks;

import uni.usic.domain.entity.tasks.*;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.util.TaskIdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskService implements TaskOperations {
    private static Map<String, Task> taskMap = new HashMap<>();
    private final String filePath;

    public TaskService(String filePath) {
        this.filePath = filePath;
    }

    public Task viewTaskById(String id, Map<String, Task> taskMap) {
        Task task = getTaskById(id, taskMap);
        if(task == null) {
            return null;
        }
        return task;
    }

    public Task getTaskById(String taskId, Map<String, Task> taskMap) {
        return taskMap.get(taskId.trim());
    }

    @Override
    public Task createTask(String ownerUsername, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        Task task;

        TaskIdGenerator taskIdGenerator = new TaskIdGenerator(filePath);
        String id = taskIdGenerator.generateId(ownerUsername);
        switch (type) {
            case STUDY -> task = new StudyTask(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, null, null, 0);
            case WORK  -> task = new WorkTask(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, null);
            case HABIT -> task = new HabitTask(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, 0, null);
            case GOAL  -> task = new GoalTask(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, null, 0);
            default -> throw new IllegalArgumentException("Unknown task type: " + type);
        }

        return task;
    }

    @Override
    public Task modifyTask(String ownerUsername, Task task, String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        task.setTitle(title);
        task.setDescription(description);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setPriority(priority);
        task.setProgress(progress);
        task.setReminderDaysBefore(reminderDaysBefore);
        return task;
    }

    @Override
    public Task updateProgress(String ownerUsername, Task task, String id, TaskProgress progress) {
        task.setProgress(progress);
        return task;
    }
}
