package uni.usic.domain.entity.tasks.worktasks;

import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.maintasks.WorkTask;
import uni.usic.domain.entity.tasks.enums.TaskPriority;

import java.time.LocalDate;

public class SkillImprovementTask extends WorkTask {
    private String skillName;
    private String currentLevel;
    private String targetLevel;
    private int progressPercentage;

    public SkillImprovementTask(String id, TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore, String careerTask, String skillName, String currentLevel, String targetLevel, int progressPercentage) {
        super(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, careerTask);
        this.skillName = skillName;
        this.currentLevel = currentLevel;
        this.targetLevel = targetLevel;
        this.progressPercentage = progressPercentage;
    }
}
