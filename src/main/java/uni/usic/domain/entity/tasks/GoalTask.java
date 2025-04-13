package uni.usic.domain.entity.tasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

public class GoalTask extends Task {
    private String target;
    private int progressPercentage;

    public GoalTask(String ownerUsername, String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, String target, int progressPercentage) {
        super(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        this.target = target;
        this.progressPercentage = progressPercentage;
    }

    @Override
    public void execute() {
        System.out.println("Progressing toward goal: " + getTitle());
        System.out.println("Target: " + target + ", Progress: " + progressPercentage + "%");
    }
}
