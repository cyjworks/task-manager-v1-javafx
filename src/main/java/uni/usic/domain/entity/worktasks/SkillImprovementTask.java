package uni.usic.domain.entity.worktasks;

import uni.usic.domain.entity.maintasks.WorkTask;
import uni.usic.domain.enums.TaskPriority;

import java.time.LocalDate;

public class SkillImprovementTask extends WorkTask {
    private String skillName;
    private String currentLevel;
    private String targetLevel;
    private int progressPercentage;

    public SkillImprovementTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        super(id, title, description, startDate, endDate, priority);
    }
}
