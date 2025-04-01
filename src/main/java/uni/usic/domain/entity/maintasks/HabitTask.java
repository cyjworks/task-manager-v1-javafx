package uni.usic.domain.entity.maintasks;

import uni.usic.domain.enums.TaskPriority;

import java.time.LocalDate;

public class HabitTask extends Task {
    private int streak;
    private String frequency;

    public HabitTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        super(id, title, description, startDate, endDate, priority);
    }

    public void incrementStreak() {

    }

    public void resetStreak() {

    }
}
