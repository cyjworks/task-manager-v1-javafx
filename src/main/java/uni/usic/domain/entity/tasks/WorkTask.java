package uni.usic.domain.entity.tasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

public class WorkTask extends Task {
    private String careerTask;

    public WorkTask(String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, String careerTask) {
        super(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        this.careerTask = careerTask;
    }

    @Override
    public void execute() {
        System.out.println("Working on: " + getTitle());
        System.out.println("Career-related task: " + careerTask);
    }
}
