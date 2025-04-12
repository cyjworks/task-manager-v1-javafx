package uni.usic.taskmanager.views.tasks;

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
import uni.usic.application.service.tasks.TaskService;
import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.infrastructure.repository.tasks.TaskFileRepository;

import java.time.LocalDate;

public class TaskModifyModal {
    public static void show(Stage ownerStage, Task task, Runnable onModified) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Modify Task");

        Label idLabel = new Label(task.getId());

        TextField titleField = new TextField(task.getTitle());
        titleField.setFont(Font.font("System", FontWeight.BOLD, 24));
        VBox topBox = new VBox(5, idLabel, titleField);
        topBox.setPadding(new Insets(0, 20, 0, 20));

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(20);
        grid.setPadding(new Insets(0, 20, 0, 20));

        ColumnConstraints col1 = new ColumnConstraints(100);
        ColumnConstraints col2 = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        col2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1, col2);

        ComboBox<TaskProgress> progressBox = new ComboBox<>();
        progressBox.getItems().addAll(TaskProgress.values());
        progressBox.setValue(task.getProgress());

        ComboBox<TaskPriority> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(TaskPriority.values());
        priorityBox.setValue(task.getPriority());

        DatePicker startDatePicker = new DatePicker(task.getStartDate());
        DatePicker endDatePicker = new DatePicker(task.getEndDate());

        // TODO: fix reminder checkbox initialisation issue
        CheckBox reminderCheckBox = new CheckBox("Enable");
        Spinner<Integer> reminderSpinner = new Spinner<>(1, 30, 3);
        reminderSpinner.setEditable(false);

        if (task.getReminderDaysBefore() != null) {
            reminderCheckBox.setSelected(true);
            reminderSpinner.setDisable(false);
            reminderSpinner.getValueFactory().setValue(task.getReminderDaysBefore());
        } else {
            reminderCheckBox.setSelected(false);
            reminderSpinner.setDisable(true);
        }

        reminderCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            reminderSpinner.setDisable(!newVal);
        });

        HBox reminderBox = new HBox(10, reminderCheckBox, reminderSpinner);
        reminderBox.setAlignment(Pos.CENTER_LEFT);

        grid.add(new Label("Progress"), 0, 0); grid.add(progressBox, 1, 0);
        grid.add(new Label("Priority"), 0, 1); grid.add(priorityBox, 1, 1);
        grid.add(new Label("Start Date"), 0, 2); grid.add(startDatePicker, 1, 2);
        grid.add(new Label("End Date"), 0, 3); grid.add(endDatePicker, 1, 3);
        grid.add(new Label("Reminder"), 0, 4); grid.add(reminderBox, 1, 4);

        TextArea descArea = new TextArea(task.getDescription());
        descArea.setWrapText(true);
        descArea.setPrefRowCount(3);
        VBox descBox = new VBox(5, new Label("Description"), descArea);
        descBox.setPadding(new Insets(0, 20, 0, 20));

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            if (confirmCancelIfModified(
                    modal,
                    titleField,
                    descArea,
                    progressBox,
                    priorityBox,
                    startDatePicker,
                    endDatePicker,
                    reminderCheckBox,
                    reminderSpinner,
                    task
            )) {
                modal.close();
            }
        });

        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(e -> handleModifyTask(
                modal,
                task,
                titleField,
                descArea,
                progressBox,
                priorityBox,
                startDatePicker,
                endDatePicker,
                reminderCheckBox,
                reminderSpinner,
                onModified
        ));

        HBox buttonBox = new HBox(10, cancelButton, modifyButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 20, 0, 20));

        modal.setOnCloseRequest(event -> {
            boolean canClose = confirmCancelIfModified(
                    modal,
                    titleField,
                    descArea,
                    progressBox,
                    priorityBox,
                    startDatePicker,
                    endDatePicker,
                    reminderCheckBox,
                    reminderSpinner,
                    task
            );

            if (!canClose) {
                event.consume(); // Prevent window from closing
            }
        });

        VBox root = new VBox(15, topBox, grid, descBox, buttonBox);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 550, 550);
        modal.setScene(scene);
        modal.initOwner(ownerStage);
        modal.showAndWait();
    }

    private static void handleModifyTask(
            Stage modal,
            Task originalTask,
            TextField titleField,
            TextArea descArea,
            ComboBox<TaskProgress> progressBox,
            ComboBox<TaskPriority> priorityBox,
            DatePicker startDatePicker,
            DatePicker endDatePicker,
            CheckBox reminderCheckBox,
            Spinner<Integer> reminderSpinner,
            Runnable onModified
    ) {
        String title = titleField.getText().trim();
        String description = descArea.getText().trim();
        TaskProgress progress = progressBox.getValue();
        TaskPriority priority = priorityBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        Integer reminder = reminderCheckBox.isSelected() ? reminderSpinner.getValue() : null;

        TaskManager taskManager = new TaskManager(
                new TaskService("src/main/java/uni/usic/infrastructure/database/tasks.txt"),
                new TaskFileRepository("src/main/java/uni/usic/infrastructure/database/tasks.txt")
        );

        Task modifiedTask = taskManager.modifyTask(originalTask.getId(),
                title,
                description,
                startDate,
                endDate,
                priority,
                progress,
                reminder);
        if(modifiedTask != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Task modified successfully!");
            alert.showAndWait();
            if(onModified != null) onModified.run();
            modal.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Task modification failed.");
            alert.showAndWait();
        }
    }

    private static boolean isModified(
            TextField titleField,
            TextArea descArea,
            ComboBox<TaskProgress> progressBox,
            ComboBox<TaskPriority> priorityBox,
            DatePicker startDatePicker,
            DatePicker endDatePicker,
            CheckBox reminderCheckBox,
            Spinner<Integer> reminderSpinner,
            Task originalTask
    ) {
        String currentTitle = titleField.getText().trim();
        String originalTitle = originalTask.getTitle().trim();

        String currentDesc = descArea.getText().trim();
        String originalDesc = originalTask.getDescription() == null ? "" : originalTask.getDescription().trim();

        boolean titleChanged = !currentTitle.equals(originalTitle);
        boolean descChanged = !currentDesc.equals(originalDesc);
        boolean progressChanged = !progressBox.getValue().equals(originalTask.getProgress());
        boolean priorityChanged = !priorityBox.getValue().equals(originalTask.getPriority());
        boolean startDateChanged = !startDatePicker.getValue().equals(originalTask.getStartDate());
        boolean endDateChanged = !endDatePicker.getValue().equals(originalTask.getEndDate());

        Integer spinnerValue = reminderCheckBox.isSelected() ? reminderSpinner.getValue() : null;
        Integer originalReminder = originalTask.getReminderDaysBefore();

        boolean reminderChanged =
                (originalReminder == null && spinnerValue != null) ||
                (originalReminder != null && !originalReminder.equals(spinnerValue));

        return titleChanged || descChanged || progressChanged || priorityChanged ||
                startDateChanged || endDateChanged || reminderChanged;
    }

    private static boolean confirmCancelIfModified(
            Stage stage,
            TextField titleField,
            TextArea descArea,
            ComboBox<TaskProgress> progressBox,
            ComboBox<TaskPriority> priorityBox,
            DatePicker startDatePicker,
            DatePicker endDatePicker,
            CheckBox reminderCheckBox,
            Spinner<Integer> reminderSpinner,
            Task originalTask
    ) {
        if (!isModified(titleField, descArea, progressBox, priorityBox, startDatePicker, endDatePicker, reminderCheckBox, reminderSpinner, originalTask)) {
            return true; // Proceed to close if no modifications are found
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage);
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText("Changes will not be saved.");
        alert.setContentText("Are you sure you want to return to the task details without saving?");

        ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesBtn, noBtn);

        return alert.showAndWait().orElse(noBtn) == yesBtn;
    }
}
