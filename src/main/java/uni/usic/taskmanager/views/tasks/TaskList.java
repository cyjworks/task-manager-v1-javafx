package uni.usic.taskmanager.views.tasks;

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
import uni.usic.application.service.tasks.TaskManager;
import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.taskmanager.views.account.ProfileView;
import uni.usic.taskmanager.views.common.MainMenuBar;

import java.util.List;

public class TaskList {
    private final TaskManager taskManager;
    private TableView<TaskItem> tableView;

    public TaskList(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void show(Stage stage) {
        Label headerLabel = new Label("Task Manager");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        BorderPane.setMargin(headerLabel, new Insets(10, 0, 10, 20));

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
//        tableView.setPrefWidth(100 + 200 + 120 + 40);
        tableView.setItems(loadTaskItems());

        TableColumn<TaskItem, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(70);

        TableColumn<TaskItem, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(160);

        TableColumn<TaskItem, String> startDateColumn = new TableColumn<>("Start Date");
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startDateColumn.setPrefWidth(100);

        TableColumn<TaskItem, String> endDateColumn = new TableColumn<>("End Date");
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        endDateColumn.setPrefWidth(100);

        TableColumn<TaskItem, String> priorityColumn = new TableColumn<>("Priority");
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        priorityColumn.setPrefWidth(80);

        TableColumn<TaskItem, String> progressColumn = new TableColumn<>("Progress");
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        progressColumn.setPrefWidth(100);

        TableColumn<TaskItem, Void> deleteColumn = new TableColumn<>();
        deleteColumn.setPrefWidth(40);
        deleteColumn.setMaxWidth(40);
        deleteColumn.setResizable(false);
        deleteColumn.setSortable(false);
        deleteColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("🗑");

            {
                deleteBtn.setOnAction(e -> {
                    TaskItem item = getTableView().getItems().get(getIndex());
                    handleDeleteTask(item, tableView);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });

        tableView.getColumns().addAll(idColumn, titleColumn, startDateColumn, endDateColumn, priorityColumn, progressColumn, deleteColumn);

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
                    TaskDetailModal.show(task, taskManager, () -> {
                        tableView.setItems(loadTaskItems());
                    });
                }
            });
            return row;
        });

        // Create button at the bottom
        Button createButton = new Button("+ Create Task");
        createButton.setOnAction(e -> {
            TaskCreateModal.show(stage, taskManager, () -> {
                tableView.setItems(loadTaskItems());
            });
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

        VBox topContainer = new VBox();
        topContainer.getChildren().addAll(
                MainMenuBar.create(
                        stage,
                        () -> this.refreshTaskList(),
                        () -> this.showProfile(stage),
                        () -> this.showCompletedTasks(),
                        () -> this.showUpcomingTasks()
                ),
                headerLabel
        );
        root.setTop(topContainer);

        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);
        stage.setTitle("Task List");
        stage.show();
    }

    private void refreshTaskList() {
        tableView.setItems(loadTaskItems());
    }

    private void showCompletedTasks() {
        List<Task> all = taskManager.viewTaskList();
        ObservableList<TaskItem> completed = FXCollections.observableArrayList();
        for (Task task : all) {
            if (task.getProgress() == TaskProgress.DONE) {
                completed.add(new TaskItem(
                        task.getId(),
                        task.getTitle(),
                        task.getStartDate().toString(),
                        task.getEndDate().toString(),
                        task.getPriority().name(),
                        task.getProgress().name()
                ));
            }
        }
        tableView.setItems(completed);
    }

    private void showUpcomingTasks() {
        List<Task> all = taskManager.viewTaskList();
        ObservableList<TaskItem> upcoming = FXCollections.observableArrayList();
        for (Task task : all) {
            if (task.getProgress() == TaskProgress.TO_DO) {
                upcoming.add(new TaskItem(
                        task.getId(),
                        task.getTitle(),
                        task.getStartDate().toString(),
                        task.getEndDate().toString(),
                        task.getPriority().name(),
                        task.getProgress().name()
                ));
            }
        }
        tableView.setItems(upcoming);
    }

    private void showProfile(Stage stage) {
        ProfileView.show(stage);
    }

    private ObservableList<TaskItem> loadTaskItems() {
        List<Task> taskList = taskManager.viewTaskList();
        ObservableList<TaskItem> taskItems = FXCollections.observableArrayList();
        for (Task task : taskList) {
            taskItems.add(new TaskItem(
                    task.getId(),
                    task.getTitle(),
                    task.getStartDate().toString(),
                    task.getEndDate().toString(),
                    task.getPriority().name(),
                    task.getProgress().name()
            ));
        }
        return taskItems;
    }

    private void handleDeleteTask(TaskItem item, TableView<TaskItem> tableView) {
        boolean confirmed = showDeleteConfirmation(item);
        if (confirmed) {
            taskManager.deleteTask(item.getId());
            tableView.getItems().remove(item);
        }
    }

    private boolean showDeleteConfirmation(TaskItem task) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Task");
        alert.setHeaderText("Are you sure you want to delete this task?");
        alert.setContentText("Task: " + task.getTitle());

        ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesBtn, noBtn);

        return alert.showAndWait().orElse(noBtn) == yesBtn;
    }
}
