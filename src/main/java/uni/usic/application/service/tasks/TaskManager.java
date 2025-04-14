package uni.usic.application.service.tasks;

import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.infrastructure.repository.tasks.TaskFileRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manages task-related operations for a specific user.
 * Provides methods to view, create, modify, and delete tasks.
 */
public class TaskManager {
    private String ownerUsername;
    private final TaskService taskService;
    private final TaskFileRepository taskFileRepository;

    /**
     * Constructs a TaskManager for a given user.
     *
     * @param ownerUsername the username of the task owner
     * @param taskService the service layer for task logic
     * @param taskFileRepository the file repository for storing tasks
     */
    public TaskManager(String ownerUsername, TaskService taskService, TaskFileRepository taskFileRepository) {
        this.ownerUsername = ownerUsername;
        this.taskService = taskService;
        this.taskFileRepository = taskFileRepository;
    }

    /**
     * Returns the list of tasks for the current user.
     *
     * @return list of tasks
     */
    public List<Task> viewTaskList() {
        return taskFileRepository.loadTaskListFromFile(ownerUsername);
    }

    /**
     * Finds a task by its ID.
     *
     * @param id the task ID
     * @return the matching task, or null if not found
     */
    public Task viewTaskById(String id) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile(ownerUsername);
        return taskService.viewTaskById(id, taskMap);
    }

    /**
     * Returns a set of unique TaskTypes used by the current user.
     *
     * @return set of unique task types
     */
    public Set<TaskType> getUniqueTaskTypes() {
        Set<TaskType> types = new HashSet<>();
        for (Task task : viewTaskList()) {
            types.add(task.getType());
        }
        return types;
    }

    /**
     * Creates a new task and saves it.
     *
     * @param type the task type
     * @param title the task title
     * @param description the task description
     * @param startDate the start date
     * @param endDate the end date
     * @param priority the task priority
     * @param progress the task progress
     * @param reminderDaysBefore reminder days before due date
     * @return the created task or null if saving failed
     */
    public Task createTask(TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        Task task = taskService.createTask(ownerUsername, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        boolean result = taskFileRepository.save(task);
        return result ? task : null;
    }

    /**
     * Modifies an existing task.
     *
     * @param id the task ID
     * @param title updated title
     * @param description updated description
     * @param startDate updated start date
     * @param endDate updated end date
     * @param priority updated priority
     * @param progress updated progress
     * @param reminderDaysBefore updated reminder
     * @return the modified task or null if not found or update failed
     */
    public Task modifyTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile(ownerUsername);
        Task taskToModify = taskService.getTaskById(id, taskMap);
        if (taskToModify == null) {
            System.out.println("No task has found like task ID: " + id);
            return null;
        }

        Task modifiedTask = taskService.modifyTask(ownerUsername, taskToModify, id, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        boolean result = taskFileRepository.update(ownerUsername, modifiedTask);
        return result ? modifiedTask : null;
    }

    /**
     * Checks if a task exists for the given ID.
     *
     * @param id the task ID
     * @return true if the task exists, false otherwise
     */
    public boolean checkIfTaskExists(String id) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile(ownerUsername);
        return taskService.getTaskById(id, taskMap) != null;
    }

    /**
     * Updates the progress of a task.
     *
     * @param id the task ID
     * @param progress new progress value
     * @return the updated task or null if update failed
     */
    public Task updateProgress(String id, TaskProgress progress) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile(ownerUsername);
        Task taskToModify = taskService.getTaskById(id, taskMap);
        if (taskToModify == null) {
            System.out.println("No task has found like task ID: " + id);
            return null;
        }

        Task updatedTask = taskService.updateProgress(ownerUsername, taskToModify, id, progress);
        boolean result = taskFileRepository.update(ownerUsername, updatedTask);
        return result ? updatedTask : null;
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the task ID
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteTask(String id) {
        boolean taskExists = checkIfTaskExists(id);
        if (!taskExists) {
            System.out.println("No task has found like task ID: " + id);
            return false;
        }

        return taskFileRepository.deleteById(ownerUsername, id);
    }
}
