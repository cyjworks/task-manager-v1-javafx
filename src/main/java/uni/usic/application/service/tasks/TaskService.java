package uni.usic.application.service.tasks;

import uni.usic.domain.entity.tasks.*;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.util.TaskIdGenerator;

import java.time.LocalDate;
import java.util.Map;

/**
 * Provides core logic for creating, updating, and retrieving tasks.
 */
public class TaskService implements TaskOperations {
    private final String filePath;

    /**
     * Constructs a TaskService with the given file path.
     *
     * @param filePath path to the task data file
     */
    public TaskService(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Retrieves a task by ID from the given task map.
     *
     * @param id the task ID
     * @param taskMap map containing all tasks
     * @return the task if found, otherwise null
     */
    public Task viewTaskById(String id, Map<String, Task> taskMap) {
        Task task = getTaskById(id, taskMap);
        if (task == null) {
            return null;
        }
        return task;
    }

    /**
     * Gets a task by ID from the map.
     *
     * @param taskId the task ID
     * @param taskMap map containing all tasks
     * @return the task if found, otherwise null
     */
    public Task getTaskById(String taskId, Map<String, Task> taskMap) {
        return taskMap.get(taskId.trim());
    }

    /**
     * Creates a new task based on the provided data.
     *
     * @param ownerUsername the owner of the task
     * @param type the type of the task
     * @param title the task title
     * @param description the task description
     * @param startDate the start date
     * @param endDate the end date
     * @param priority the task priority
     * @param progress the task progress
     * @param reminderDaysBefore number of days before to remind
     * @return the created task
     */
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

    /**
     * Modifies an existing task with new values.
     *
     * @param ownerUsername the owner of the task
     * @param task the task to modify
     * @param id the task ID
     * @param title new title
     * @param description new description
     * @param startDate new start date
     * @param endDate new end date
     * @param priority new priority
     * @param progress new progress
     * @param reminderDaysBefore new reminder value
     * @return the modified task
     */
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

    /**
     * Updates the progress of the given task.
     *
     * @param ownerUsername the owner of the task
     * @param task the task to update
     * @param id the task ID
     * @param progress new progress value
     * @return the updated task
     */
    @Override
    public Task updateProgress(String ownerUsername, Task task, String id, TaskProgress progress) {
        task.setProgress(progress);
        return task;
    }
}
