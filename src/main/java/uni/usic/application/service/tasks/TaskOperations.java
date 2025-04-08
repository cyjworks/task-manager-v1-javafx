package uni.usic.application.service.tasks;

import uni.usic.domain.entity.tasks.maintasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;

import java.time.LocalDate;
import java.util.List;

public interface TaskOperations {
    List<Task> viewTaskList();
    void viewTask(String id);
    Task createTask(String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority);
    Task modifyTask(Task task, String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore);
    Task updateProgress(Task task, String id, TaskProgress progress);
    boolean deleteTask(String id);
}
