package uni.usic.domain.entity.tasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

/**
 * Represents a study task with subject, type, and estimated time.
 * Inherits from the base Task class.
 */
public class StudyTask extends Task {
    private String subject;
    private String studyType;
    private int estimateTime;

    /**
     * Constructs a new StudyTask.
     *
     * @param ownerUsername the username of the task owner
     * @param id the task ID
     * @param type the task type
     * @param title the task title
     * @param description the task description
     * @param startDate the start date
     * @param endDate the end date
     * @param priority the task priority
     * @param progress the task progress
     * @param reminderDaysBefore days before due date to trigger reminder
     * @param subject the subject of the study task
     * @param studyType the study type (e.g., Reading, Practice)
     * @param estimateTime estimated time to complete the task (minutes)
     */
    public StudyTask(String ownerUsername, String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, String subject, String studyType, int estimateTime) {
        super(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        this.subject = subject;
        this.studyType = studyType;
        this.estimateTime = estimateTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public int getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(int estimateTime) {
        this.estimateTime = estimateTime;
    }

    /**
     * Executes the study task by printing task details.
     */
    @Override
    public void execute() {
        System.out.println("Starting study task: " + getTitle());
        System.out.println("Subject: " + subject + ", Type: " + studyType + ", Estimated time: " + estimateTime + " mins");
    }
}
