package uni.usic.domain.entity.tasks;

import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.time.LocalDate;

public class HabitTask extends Task {
    private int streak;
    private String frequency;

    public HabitTask(String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, int streak, String frequency) {
        super(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        this.streak = streak;
        this.frequency = frequency;
    }

    public void incrementStreak() {

    }

    public void resetStreak() {

    }
}
