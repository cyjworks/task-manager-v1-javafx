package uni.usic.infrastructure.repository;

import uni.usic.domain.entity.maintasks.Task;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;

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
