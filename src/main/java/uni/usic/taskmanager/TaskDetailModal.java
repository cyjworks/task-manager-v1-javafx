package uni.usic.taskmanager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TaskDetailModal {
    public static void show(TaskItem task) {
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
}
