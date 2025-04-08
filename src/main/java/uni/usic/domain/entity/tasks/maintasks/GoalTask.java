package uni.usic.domain.entity.tasks.maintasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;

import java.time.LocalDate;

public class GoalTask extends Task {
    private String target;
    private int progressPercentage;

    public GoalTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        super(id, title, description, startDate, endDate, priority);
    }
}
