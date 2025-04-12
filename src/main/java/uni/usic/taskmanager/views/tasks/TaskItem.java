package uni.usic.taskmanager.views.tasks;

public class TaskItem {
    private final String id;
    private final String title;
    private String startDate;
    private String endDate;
    private String priority;
    private final String progress;

    public TaskItem(String id, String title, String startDate, String endDate, String priority, String progress) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.progress = progress;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getProgress() {
        return progress;
    }
}
