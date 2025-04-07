package uni.usic.taskmanager;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uni.usic.application.service.TaskManager;
import uni.usic.application.service.TaskService;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;
import uni.usic.infrastructure.repository.TaskFileRepository;

import java.time.LocalDate;

public class TaskCreateModal {
    private static final String TASKS_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/tasks.txt";
    private static final TaskManager taskManager = new TaskManager(new TaskService(TASKS_FILE_PATH), new TaskFileRepository(TASKS_FILE_PATH));

    public static void show(Stage ownerStage) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Create Task");

        Label idLabel = new Label("TASK-0");
        // Top title input
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        titleField.setFont(Font.font("System", FontWeight.BOLD, 24));
        VBox topBox = new VBox(5, idLabel, titleField);
        topBox.setPadding(new Insets(0, 20, 0, 20));

        // Grid section for form fields
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 20, 0, 20));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(100);
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(200);
        col2.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(col1, col2);


        // Input fields for task attributes
        ComboBox<TaskProgress> progressBox = new ComboBox<>();
        progressBox.getItems().addAll(TaskProgress.values());
        progressBox.setValue(TaskProgress.TO_DO);

        ComboBox<TaskPriority> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(TaskPriority.values());
        priorityBox.setValue(TaskPriority.MEDIUM);

        DatePicker startDatePicker = new DatePicker(LocalDate.now());
        DatePicker endDatePicker = new DatePicker(LocalDate.now().plusDays(1));

        Spinner<Integer> reminderSpinner = new Spinner<>(0, 30, 3);
        reminderSpinner.setEditable(true);

        grid.add(new Label("Progress"), 0, 0);
        grid.add(progressBox, 1, 0);

        grid.add(new Label("Priority"), 0, 1);
        grid.add(priorityBox, 1, 1);

        grid.add(new Label("Start Date"), 0, 2);
        grid.add(startDatePicker, 1, 2);

        grid.add(new Label("End Date"), 0, 3);
        grid.add(endDatePicker, 1, 3);

        grid.add(new Label("Reminder"), 0, 4);
        grid.add(reminderSpinner, 1, 4);

        // Description text area
        Label descLabel = new Label("Description");
        TextArea descArea = new TextArea();
        descArea.setWrapText(true);
        descArea.setPrefRowCount(3);
        VBox descBox = new VBox(5, descLabel, descArea);
        descBox.setPadding(new Insets(0, 20, 0, 20));

        // Create button
        Button createButton = new Button("Create");
        createButton.setOnAction(e -> {
            System.out.println("Title: " + titleField.getText());
            System.out.println("Progress: " + progressBox.getValue());
            System.out.println("Priority: " + priorityBox.getValue());
            System.out.println("Start: " + startDatePicker.getValue());
            System.out.println("End: " + endDatePicker.getValue());
            System.out.println("Reminder: " + reminderSpinner.getValue());
            System.out.println("Description: " + descArea.getText());

            // TODO: Connect to TaskManager.createTask(...) to persist the created task.
            modal.close();
        });

        HBox buttonBox = new HBox(createButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 20, 0, 20));

        // Main container
        VBox root = new VBox(15, topBox, grid, descBox, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        Platform.runLater(() -> root.requestFocus());

        Scene scene = new Scene(root, 450, 500);
        modal.setScene(scene);
        modal.initOwner(ownerStage);
        modal.showAndWait();
    }
}
