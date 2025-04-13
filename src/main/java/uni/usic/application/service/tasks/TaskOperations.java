package uni.usic.application.service.tasks;

import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;

import java.time.LocalDate;
import java.util.List;

public interface TaskOperations {
    List<Task> viewTaskList(String ownerUsername);
    void viewTask(String ownerUsername, String id);
    Task createTask(String ownerUsername, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore);
    Task modifyTask(String ownerUsername, Task task, String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore);
    Task updateProgress(String ownerUsername, Task task, String id, TaskProgress progress);
    boolean deleteTask(String ownerUsername, String id);
}
