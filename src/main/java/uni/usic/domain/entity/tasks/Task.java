package uni.usic.domain.entity.tasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

/**
 * Represents a general task entity with basic attributes.
 * This is the base class for all specific task types.
 */
public abstract class Task {
    private String ownerUsername;
    private String id;
    private TaskType type;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskPriority priority;
    private TaskProgress progress;
    private Integer reminderDaysBefore;

    /**
     * Constructs a Task with the given attributes.
     *
     * @param ownerUsername the username of the task owner
     * @param id unique identifier for the task
     * @param type task type
     * @param title task title
     * @param description task description
     * @param startDate start date of the task
     * @param endDate end date of the task
     * @param priority task priority
     * @param progress task progress
     * @param reminderDaysBefore days before deadline to remind
     */
    public Task(String ownerUsername, String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        this.ownerUsername = ownerUsername;
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.progress = progress;
        this.reminderDaysBefore = reminderDaysBefore;
    }

    // Getters and Setters

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskProgress getProgress() {
        return progress;
    }

    public void setProgress(TaskProgress progress) {
        this.progress = progress;
    }

    public Integer getReminderDaysBefore() {
        return reminderDaysBefore;
    }

    public void setReminderDaysBefore(Integer reminderDaysBefore) {
        this.reminderDaysBefore = reminderDaysBefore;
    }

    @Override
    public String toString() {
        return "Task ID: " + id +
                "\nType: " + type +
                "\nTitle: " + title +
                "\nDescription: " + description +
                "\nStart Date: " + startDate +
                "\nEnd Date: " + endDate +
                "\nPriority: " + priority +
                "\nProgress: " + progress +
                "\nReminder Days Before: " + (reminderDaysBefore == null ? "Not set" : reminderDaysBefore);
    }

    /**
     * Executes the task.
     */
    public abstract void execute();

    /**
     * Sets a reminder for the task a specified number of days before the deadline.
     *
     * @param daysBefore number of days before deadline
     */
    public void setTaskReminder(int daysBefore) {
        this.reminderDaysBefore = daysBefore;
    }

    /**
     * Checks if the reminder should be triggered today.
     *
     * @param daysBefore number of days before deadline
     * @return true if reminder is due today, false otherwise
     */
    public boolean isReminderDue(int daysBefore) {
        if(reminderDaysBefore == null) return false;
        LocalDate today = LocalDate.now();
        return today.equals(endDate.minusDays(daysBefore));
    }

}
