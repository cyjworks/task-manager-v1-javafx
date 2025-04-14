package uni.usic.application.service.tasks;

import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;

import java.time.LocalDate;

/**
 * Defines basic operations for task management.
 */
public interface TaskOperations {

    /**
     * Creates a new task with given information.
     *
     * @param ownerUsername username of the task owner
     * @param type task type
     * @param title task title
     * @param description task description
     * @param startDate task start date
     * @param endDate task end date
     * @param priority task priority
     * @param progress task progress status
     * @param reminderDaysBefore number of days before due date to trigger reminder
     * @return created task object
     */
    Task createTask(String ownerUsername, TaskType type, String title, String description,
                    LocalDate startDate, LocalDate endDate, TaskPriority priority,
                    TaskProgress progress, Integer reminderDaysBefore);

    /**
     * Modifies an existing task with new values.
     *
     * @param ownerUsername username of the task owner
     * @param task original task object
     * @param id task ID
     * @param title new title
     * @param description new description
     * @param startDate new start date
     * @param endDate new end date
     * @param priority new priority
     * @param progress new progress
     * @param reminderDaysBefore updated reminder days
     * @return modified task object
     */
    Task modifyTask(String ownerUsername, Task task, String id, String title, String description,
                    LocalDate startDate, LocalDate endDate, TaskPriority priority,
                    TaskProgress progress, Integer reminderDaysBefore);

    /**
     * Updates only the progress of a task.
     *
     * @param ownerUsername username of the task owner
     * @param task task to update
     * @param id task ID
     * @param progress new progress value
     * @return task with updated progress
     */
    Task updateProgress(String ownerUsername, Task task, String id, TaskProgress progress);
}
