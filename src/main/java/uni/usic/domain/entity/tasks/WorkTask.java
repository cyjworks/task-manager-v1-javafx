package uni.usic.domain.entity.tasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

/**
 * Represents a work task related to career development.
 * Inherits from the base Task class.
 */
public class WorkTask extends Task {
    private String careerTask;

    /**
     * Constructs a new WorkTask.
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
     * @param careerTask the description of the career-related task
     */
    public WorkTask(String ownerUsername, String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, String careerTask) {
        super(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        this.careerTask = careerTask;
    }

    // Getters and Setters

    public String getCareerTask() {
        return careerTask;
    }

    public void setCareerTask(String careerTask) {
        this.careerTask = careerTask;
    }

    /**
     * Executes the work task by printing task details.
     */
    @Override
    public void execute() {
        System.out.println("Working on: " + getTitle());
        System.out.println("Career-related task: " + careerTask);
    }
}
