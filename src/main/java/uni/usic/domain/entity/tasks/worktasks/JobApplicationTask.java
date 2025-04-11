package uni.usic.domain.entity.tasks.worktasks;

import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.maintasks.WorkTask;
import uni.usic.domain.entity.tasks.enums.TaskPriority;

import java.time.LocalDate;

public class JobApplicationTask extends WorkTask {
    private String company;
    private String position;
    private String status;
    private LocalDate interviewDate;

    public JobApplicationTask(String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, String careerTask, String company, String position, String status, LocalDate interviewDate) {
        super(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, careerTask);
        this.company = company;
        this.position = position;
        this.status = status;
        this.interviewDate = interviewDate;
    }
}
