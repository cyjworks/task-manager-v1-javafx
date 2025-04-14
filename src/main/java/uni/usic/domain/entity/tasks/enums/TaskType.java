package uni.usic.domain.entity.tasks.enums;

/**
 * Enum representing types of tasks.
 */
public enum TaskType {
    STUDY, WORK, HABIT, GOAL;

    /**
     * Converts a string to its corresponding TaskType enum.
     *
     * @param str the input string
     * @return the corresponding TaskType
     * @throws IllegalArgumentException if the string does not match any TaskType
     */
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
