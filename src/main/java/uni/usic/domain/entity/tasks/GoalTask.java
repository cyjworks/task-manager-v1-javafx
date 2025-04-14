package uni.usic.domain.entity.tasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

/**
 * Represents a goal-oriented task with a measurable target and progress.
 * Inherits from the base Task class.
 */
public class GoalTask extends Task {
    private String target;
    private int progressPercentage;

    /**
     * Constructs a new GoalTask.
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
     * @param target the goal target description
     * @param progressPercentage the percentage of progress toward the goal
     */
    public GoalTask(String ownerUsername, String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, String target, int progressPercentage) {
        super(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        this.target = target;
        this.progressPercentage = progressPercentage;
    }

    // Getters and Setters

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    /**
     * Executes the goal task by printing target and progress.
     */
    @Override
    public void execute() {
        System.out.println("Progressing toward goal: " + getTitle());
        System.out.println("Target: " + target + ", Progress: " + progressPercentage + "%");
    }
}
