package uni.usic.domain.entity.tasks.maintasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;

import java.time.LocalDate;

public class WorkTask extends Task {
    private String careerTask;

    public WorkTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        super(id, title, description, startDate, endDate, priority);
    }
}
