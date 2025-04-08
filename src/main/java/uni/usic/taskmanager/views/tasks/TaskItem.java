package uni.usic.taskmanager.views.tasks;

public class TaskItem {
    private final String id;
    private final String title;
    private final String progress;

    public TaskItem(String id, String title, String progress) {
        this.id = id;
        this.title = title;
        this.progress = progress;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getProgress() { return progress; }
}
