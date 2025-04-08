package uni.usic.domain.entity.tasks.worktasks;

import uni.usic.domain.entity.tasks.maintasks.WorkTask;
import uni.usic.domain.entity.tasks.enums.TaskPriority;

import java.time.LocalDate;

public class JobApplicationTask extends WorkTask {
    private String company;
    private String position;
    private String status;
    private LocalDate interviewDate;

    public JobApplicationTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        super(id, title, description, startDate, endDate, priority);
    }
}
