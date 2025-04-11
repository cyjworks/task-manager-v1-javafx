package uni.usic.domain.entity.tasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

public class StudyTask extends Task {
    private String subject;
    private String studyType;
    private int estimateTime;

    public StudyTask(String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, String subject, String studyType, int estimateTime) {
        super(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        this.subject = subject;
        this.studyType = studyType;
        this.estimateTime = estimateTime;
    }

    public void startStudying() {

    }
}
