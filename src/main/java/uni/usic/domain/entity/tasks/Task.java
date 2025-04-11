package uni.usic.domain.entity.tasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

public abstract class Task {
    private String id;
    private TaskType type;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskPriority priority;
    private TaskProgress progress;
    private Integer reminderDaysBefore;

    public Task(String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
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
        return "Task: " + title +
                "\nDescription: " + description +
                "\nStart Date: " + startDate +
                "\nEnd Date: " + endDate +
                "\nPriority: " + priority +
                "\nProgress: " + progress +
                "\nReminder Days Before: " + (reminderDaysBefore == null ? "Not set" : reminderDaysBefore);
    }

    public abstract void execute();

    /**
     * Sets a reminder for the task a specified number of days before its due date.
     *
     * @param daysBefore the number of days before the task's end date when the reminder should be triggered
     */
    public void setTaskReminder(int daysBefore) {
        this.reminderDaysBefore = daysBefore;
//        if(isReminderDue(daysBefore)) {
//            System.out.println("Reminder: Task '" + title + "' is due in " + daysBefore + " days!");
//        }
    }

    /**
     * Checks if the reminder for this task is due today.
     *
     * @return true if the reminder should be triggered today, false otherwise
     */
    public boolean isReminderDue(int daysBefore) {
        if(reminderDaysBefore == null) return false;
        LocalDate today = LocalDate.now();
        return today.equals(endDate.minusDays(daysBefore));
    }

}
