package uni.usic.domain.entity.tasks.maintasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

public class GoalTask extends Task {
    private String target;
    private int progressPercentage;

    public GoalTask(String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, String target, int progressPercentage) {
        super(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        this.target = target;
        this.progressPercentage = progressPercentage;
    }
}
