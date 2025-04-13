package uni.usic.infrastructure.repository.tasks;

import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    // create
    boolean save(Task task);

    // read
    List<Task> findAll(String ownerUsername);
    Optional<Task> findById(String ownerUsername, String taskId);
    List<Task> findByPriority(String ownerUsername, TaskPriority priority);
    List<Task> findByProgress(String ownerUsername, TaskProgress progress);
    List<Task> findByDateRange(String ownerUsername, LocalDate start, LocalDate end);

    // update
    boolean update(String ownerUsername, Task task);

    // delete
    boolean deleteById(String ownerUsername, String taskId);
    void deleteAll(String ownerUsername);
}
