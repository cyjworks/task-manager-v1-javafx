package uni.usic.domain.entity.tasks.maintasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;

import java.time.LocalDate;

public class StudyTask extends Task {
    private String subject;
    private String studyType;
    private int estimateTime;

    public StudyTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        super(id, title, description, startDate, endDate, priority);
    }

    public void startStudying() {

    }
}
