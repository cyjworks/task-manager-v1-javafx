package uni.usic.domain.entity.maintasks;

import uni.usic.domain.enums.TaskPriority;

import java.time.LocalDate;

public class GoalTask extends Task {
    private String target;
    private int progressPercentage;

    public GoalTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        super(id, title, description, startDate, endDate, priority);
    }
}
