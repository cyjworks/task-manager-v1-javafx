package uni.usic.domain.entity.tasks.enums;

public enum TaskType {
    STUDY, WORK, HABIT, GOAL;

    public static TaskType fromString(String str) {
        return switch (str.toUpperCase()) {
            case "STUDY" -> STUDY;
            case "WORK" -> WORK;
            case "HABIT" -> HABIT;
            case "GOAL" -> GOAL;
            default -> throw new IllegalArgumentException("Unknown TaskType: " + str);
        };
    }
}
