package uni.usic.domain.entity.tasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

/**
 * Represents a habit task to track daily or periodic habits.
 * Inherits from the base Task class.
 */
public class HabitTask extends Task {
    private int streak;
    private String frequency;

    /**
     * Constructs a new HabitTask.
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
     * @param streak the current habit streak
     * @param frequency the frequency of the habit (e.g., daily, weekly)
     */
    public HabitTask(String ownerUsername, String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, int streak, String frequency) {
        super(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        this.streak = streak;
        this.frequency = frequency;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     * Executes the habit task by printing and incrementing the streak.
     */
    @Override
    public void execute() {
        System.out.println("Tracking habit: " + getTitle());
        incrementStreak();
        System.out.println("Current streak: " + streak);
    }

    /**
     * Increments the streak count by 1.
     */
    public void incrementStreak() {
        this.streak++;
    }

    /**
     * Resets the streak count to 0.
     */
    public void resetStreak() {
        this.streak = 0;
    }
}
