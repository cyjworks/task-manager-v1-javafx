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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TaskList {
    public void show(Stage stage) {
        Label headerLabel = new Label("Task Manager");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        BorderPane.setMargin(headerLabel, new Insets(10, 0, 10, 20));

        TableView<TaskItem> tableView = new TableView<>();
        ObservableList<TaskItem> taskItems = FXCollections.observableArrayList(
                new TaskItem("TASK-1", "Study Java", "IN_PROGRESS"),
                new TaskItem("TASK-2", "Write Report", "TO_DO"),
                new TaskItem("TASK-3", "Team Meeting", "DONE")
        );

        TableColumn<TaskItem, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(100);

        TableColumn<TaskItem, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(200);

        TableColumn<TaskItem, String> progressColumn = new TableColumn<>("Progress");
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        progressColumn.setPrefWidth(120);

        tableView.setItems(taskItems);
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
                    showTaskDetailsModal(clickedTask);
                }
            });
            return row;
        });

        // Create button at the bottom
        Button createButton = new Button("+ Create Task");
        createButton.setOnAction(e -> {
            // TODO: Link to Create Task screen
            System.out.println("Create Task clicked");
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

    // Task details modal
    private void showTaskDetailsModal(TaskItem task) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Task Details");

        Label details = new Label(
                "ID: " + task.getId() + "\n" +
                        "Title: " + task.getTitle() + "\n" +
                        "Progress: " + task.getProgress()
        );
        details.setPadding(new Insets(10));

        VBox layout = new VBox(details);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 200);
        modal.setScene(scene);
        modal.showAndWait();
    }

    // Inner class TaskItem (simple DTO)
    public static class TaskItem {
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
}
