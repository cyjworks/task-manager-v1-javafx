package uni.usic.taskmanager.views.tasks;

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
import uni.usic.application.service.tasks.TaskManager;
import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;

import java.time.LocalDate;

public class TaskCreateModal {
    public static void show(Stage ownerStage, TaskManager taskManager, Runnable onTaskCreated) {
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
        grid.setVgap(20);
        grid.setPadding(new Insets(0, 20, 0, 20));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(100);
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(200);
        col2.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(col1, col2);


        // Input fields for task attributes

        // Task Type
        Label typeLabel = new Label("Task Type");
        RadioButton studyRadio = new RadioButton("Study");
        RadioButton workRadio = new RadioButton("Work");
        RadioButton habitRadio = new RadioButton("Habit");
        RadioButton goalRadio = new RadioButton("Goal");

        ToggleGroup typeGroup = new ToggleGroup();
        studyRadio.setToggleGroup(typeGroup);
        workRadio.setToggleGroup(typeGroup);
        habitRadio.setToggleGroup(typeGroup);
        goalRadio.setToggleGroup(typeGroup);

        studyRadio.setSelected(true);

        HBox typeBox = new HBox(15, studyRadio, workRadio, habitRadio, goalRadio);
        typeBox.setAlignment(Pos.CENTER_LEFT);
        VBox typeSection = new VBox(5, typeLabel, typeBox);
        typeSection.setPadding(new Insets(0, 20, 0, 20));

        // Progress
        ComboBox<TaskProgress> progressBox = new ComboBox<>();
        progressBox.getItems().addAll(TaskProgress.values());
        progressBox.setValue(TaskProgress.TO_DO);

        // Priority
        ComboBox<TaskPriority> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(TaskPriority.values());
        priorityBox.setValue(TaskPriority.MEDIUM);

        // Start date, End date
        DatePicker startDatePicker = new DatePicker(LocalDate.now());
        DatePicker endDatePicker = new DatePicker(LocalDate.now().plusDays(1));

        // Reminder
        CheckBox reminderCheckBox = new CheckBox("Enable");
        Spinner<Integer> reminderSpinner = new Spinner<>(1, 30, 3);
        reminderSpinner.setDisable(true); // Disabled by default

        reminderCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            reminderSpinner.setDisable(!newVal);
        });

        HBox reminderBox = new HBox(10, reminderCheckBox, reminderSpinner);
        reminderBox.setAlignment(Pos.CENTER_LEFT);

        grid.add(new Label("Progress"), 0, 0);
        grid.add(progressBox, 1, 0);

        grid.add(new Label("Priority"), 0, 1);
        grid.add(priorityBox, 1, 1);

        grid.add(new Label("Start Date"), 0, 2);
        grid.add(startDatePicker, 1, 2);

        grid.add(new Label("End Date"), 0, 3);
        grid.add(endDatePicker, 1, 3);

        grid.add(new Label("Reminder"), 0, 4);
        grid.add(reminderBox, 1, 4);

        // Description text area
        Label descLabel = new Label("Description");
        TextArea descArea = new TextArea();
        descArea.setWrapText(true);
        descArea.setPrefRowCount(3);
        VBox descBox = new VBox(5, descLabel, descArea);
        descBox.setPadding(new Insets(0, 20, 0, 20));

        // Create button
        Button createButton = new Button("Create");
        createButton.setOnAction(e -> handleCreateTask(
                taskManager,
                titleField,
                descArea,
                startDatePicker,
                endDatePicker,
                priorityBox,
                progressBox,
                reminderSpinner,
                typeGroup,
                modal,
                onTaskCreated
        ));

        HBox buttonBox = new HBox(createButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 20, 0, 20));

        // Main container
        VBox root = new VBox(15, topBox, typeSection, grid, descBox, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        Platform.runLater(() -> root.requestFocus());

        Scene scene = new Scene(root, 550, 550);
        modal.setScene(scene);
        modal.initOwner(ownerStage);
        modal.showAndWait();
    }

    private static void handleCreateTask(
            TaskManager taskManager,
            TextField titleField,
            TextArea descArea,
            DatePicker startDatePicker,
            DatePicker endDatePicker,
            ComboBox<TaskPriority> priorityBox,
            ComboBox<TaskProgress> progressBox,
            Spinner<Integer> reminderSpinner,
            ToggleGroup typeGroup,
            Stage modal,
            Runnable onTaskCreated
    ) {
        String title = titleField.getText();
        String description = descArea.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        TaskPriority priority = priorityBox.getValue();
        TaskProgress progress = progressBox.getValue();
        Integer reminderDaysBefore = reminderSpinner.getValue();

        String typeStr = ((RadioButton) typeGroup.getSelectedToggle()).getText();
        TaskType type = TaskType.fromString(typeStr);
        Task createdTask = taskManager.createTask(type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        if (createdTask != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Task created successfully!");
            alert.showAndWait();
            if (onTaskCreated != null) onTaskCreated.run(); // refresh UI
            modal.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Task creation failed.");
            alert.showAndWait();
        }
    }
}
