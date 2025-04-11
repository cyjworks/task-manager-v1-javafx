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
    List<Task> findAll();
    Optional<Task> findById(String taskId);
    List<Task> findByPriority(TaskPriority priority);
    List<Task> findByProgress(TaskProgress progress);
    List<Task> findByDateRange(LocalDate start, LocalDate end);

    // update
    boolean update(Task task);

    // delete
    boolean deleteById(String taskId);
    void deleteAll();
}
