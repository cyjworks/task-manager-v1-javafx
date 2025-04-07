package uni.usic.taskmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import uni.usic.application.service.TaskManager;
import uni.usic.application.service.TaskService;
import uni.usic.domain.entity.maintasks.Task;
import uni.usic.infrastructure.repository.TaskFileRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskList {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final String TASKS_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/tasks.txt";

    // Dependency Injection
    private static final TaskFileRepository taskFileRepository = new TaskFileRepository(TASKS_FILE_PATH);
    private static final TaskService taskService = new TaskService(TASKS_FILE_PATH);
    private static final TaskManager taskManager = new TaskManager(taskService, taskFileRepository);

    public void show(Stage stage) {
        Label headerLabel = new Label("Task Manager");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        BorderPane.setMargin(headerLabel, new Insets(10, 0, 10, 20));

        TableView<TaskItem> tableView = new TableView<>();
        tableView.setItems(loadTaskItems());

        TableColumn<TaskItem, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(100);

        TableColumn<TaskItem, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(200);

        TableColumn<TaskItem, String> progressColumn = new TableColumn<>("Progress");
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        progressColumn.setPrefWidth(120);

        tableView.getColumns().addAll(idColumn, titleColumn, progressColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Set padding around the table
        VBox centerBox = new VBox(tableView);
        centerBox.setPadding(new Insets(10, 20, 10, 20));

        // Double-click a task → show details modal
        tableView.setRowFactory(tv -> {
            TableRow<TaskItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    TaskItem clickedTask = row.getItem();
                    Task task = taskManager.viewTaskById(clickedTask.getId());
                    TaskDetailModal.show(task);
                }
            });
            return row;
        });

        // Create button at the bottom
        Button createButton = new Button("+ Create Task");
        createButton.setOnAction(e -> {
            TaskCreateModal.show(stage);
        });

        HBox buttonBox = new HBox(createButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setPadding(new Insets(10, 20, 10, 20));

        BorderPane root = new BorderPane();
        root.setTop(headerLabel);
//        root.setCenter(tableView);
        root.setCenter(centerBox);
        root.setBottom(buttonBox);
//        root.setPadding(new Insets(20));  // Apply padding to the main window

        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Task List");
        stage.show();
    }

    private ObservableList<TaskItem> loadTaskItems() {
        List<Task> taskList = taskManager.viewTaskList();
        ObservableList<TaskItem> taskItems = FXCollections.observableArrayList();
        for (Task task : taskList) {
            taskItems.add(new TaskItem(task.getId(), task.getTitle(), task.getProgress().name()));
        }
        return taskItems;
    }
}
