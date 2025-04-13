package uni.usic.infrastructure.repository.tasks;

import uni.usic.domain.entity.tasks.Task;

import java.util.List;
import java.util.Optional;

/**
 * Interface for task data access operations.
 */
public interface TaskRepository {

    /**
     * Saves a new task to storage.
     *
     * @param task task to save
     * @return true if saved successfully, false otherwise
     */
    boolean save(Task task);

    /**
     * Finds all tasks for the given user.
     *
     * @param ownerUsername username of the task owner
     * @return list of tasks
     */
    List<Task> findAll(String ownerUsername);

    /**
     * Finds a task by ID for the given user.
     *
     * @param ownerUsername username of the task owner
     * @param taskId ID of the task to find
     * @return optional task object
     */
    Optional<Task> findById(String ownerUsername, String taskId);

    /**
     * Updates an existing task.
     *
     * @param ownerUsername username of the task owner
     * @param task updated task object
     * @return true if updated successfully, false otherwise
     */
    boolean update(String ownerUsername, Task task);

    /**
     * Deletes a task by ID.
     *
     * @param ownerUsername username of the task owner
     * @param taskId ID of the task to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteById(String ownerUsername, String taskId);

    /**
     * Deletes all tasks for the given user.
     *
     * @param ownerUsername username of the task owner
     */
    void deleteAll(String ownerUsername);
}
