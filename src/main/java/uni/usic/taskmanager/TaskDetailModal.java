package uni.usic.taskmanager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uni.usic.domain.entity.maintasks.Task;

public class TaskDetailModal {
    public static void show(Task task) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Task Details");

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

//        Font labelFont = Font.font("System", FontWeight.BOLD, 14);
//        Font valueFont = Font.font("System", FontWeight.NORMAL, 14);

        // Row 0
//        Label idLabel = new Label("ID");
//        idLabel.setFont(labelFont);
        grid.add(new Label("ID"), 0, 0);
        grid.add(new Label(task.getId()), 1, 0);

        // Row 1
        grid.add(new Label("Title"), 0, 1);
        grid.add(new Label(task.getTitle()), 1, 1);

        // Row 2
        grid.add(new Label("Description"), 0, 2);
        grid.add(new Label(task.getDescription()), 1, 2);

        // Row 3
        grid.add(new Label("Start Date"), 0, 3);
        grid.add(new Label(task.getStartDate().toString()), 1, 3);

        // Row 4
        grid.add(new Label("End Date"), 0, 4);
        grid.add(new Label(task.getEndDate().toString()), 1, 4);

        // Row 5
        grid.add(new Label("Priority"), 0, 5);
        grid.add(new Label(task.getPriority().toString()), 1, 5);

        // Row 6
        grid.add(new Label("Progress"), 0, 6);
        grid.add(new Label(task.getProgress().toString()), 1, 6);

        // Row 7
        grid.add(new Label("Reminder"), 0, 7);
        String reminderText = (task.getReminderDaysBefore() == null)
                ? "Not set"
                : task.getReminderDaysBefore() + " days before";
        grid.add(new Label(reminderText), 1, 7);
//        grid.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

        VBox root = new VBox(grid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 350);
        modal.setScene(scene);
        modal.showAndWait();
    }
}
