package uni.usic.domain.entity.tasks.worktasks;

import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.maintasks.WorkTask;
import uni.usic.domain.entity.tasks.enums.TaskPriority;

import java.time.LocalDate;

public class CurrentJobTask extends WorkTask {
    private LocalDate deadline;

    public CurrentJobTask(String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, String careerTask, LocalDate deadline) {
        super(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, careerTask);
        this.deadline = deadline;
    }
}
