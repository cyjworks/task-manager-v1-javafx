package uni.usic.taskmanager.views.tasks;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uni.usic.domain.entity.tasks.Task;

public class TaskDetailModal {
    public static void show(Task task, Runnable onModified) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Task Details");

        Label idLabel = new Label(task.getId());
        Label titleLabel = new Label(task.getTitle());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        VBox topBox = new VBox(5, idLabel, titleLabel);
        topBox.setPadding(new Insets(0, 20, 0, 20));

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 20, 0, 20));

        // Set column widths
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(100); // Label 쪽
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(200); // 값 쪽
        col2.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(col1, col2);

        grid.add(new Label("Progress"), 0, 0);
        grid.add(new Label(task.getProgress().toString()), 1, 0);

        grid.add(new Label("Priority"), 0, 1);
        grid.add(new Label(task.getPriority().toString()), 1, 1);

        grid.add(new Label("Start Date"), 0, 2);
        grid.add(new Label(task.getStartDate().toString()), 1, 2);

        grid.add(new Label("End Date"), 0, 3);
        grid.add(new Label(task.getEndDate().toString()), 1, 3);

        grid.add(new Label("Reminder"), 0, 4);
        String reminderText = (task.getReminderDaysBefore() == null)
                ? "Not set"
                : task.getReminderDaysBefore() + " days before";
        grid.add(new Label(reminderText), 1, 4);

        Label descLabel = new Label("Description");
        TextArea descArea = new TextArea(task.getDescription());
        descArea.setWrapText(true);
        descArea.setEditable(false);
        descArea.setPrefRowCount(3);
        VBox descBox = new VBox(5, descLabel, descArea);
        descBox.setPadding(new Insets(0, 20, 0, 20));

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            modal.close();
        });
        // Modify button
        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(e -> {
            TaskModifyModal.show(modal, task, () -> {
                // TODO: refresh detail modal
                if (onModified != null) onModified.run();
            });
        });

        HBox buttonBox = new HBox(5, cancelButton, modifyButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 20, 0, 20));

        VBox root = new VBox(15, topBox, grid, descBox, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 400);
        modal.setScene(scene);
        modal.showAndWait();
    }
}
