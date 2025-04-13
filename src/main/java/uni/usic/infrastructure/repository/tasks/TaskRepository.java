package uni.usic.infrastructure.repository.tasks;

import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    boolean save(Task task);
    List<Task> findAll(String ownerUsername);
    Optional<Task> findById(String ownerUsername, String taskId);
    boolean update(String ownerUsername, Task task);
    boolean deleteById(String ownerUsername, String taskId);
    void deleteAll(String ownerUsername);
}
