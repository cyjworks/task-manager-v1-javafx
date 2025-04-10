package uni.usic.taskmanager.views.account;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProfileView {
    public static void show(Stage owner) {
        Stage stage = new Stage();
        stage.setTitle("User Profile");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);

        Label title = new Label("User Profile");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ImageView avatar = new ImageView(new Image("file:src/main/resources/images/profile.png"));
        avatar.setFitWidth(120);
        avatar.setFitHeight(120);

        Label name = new Label("Name: Test User");
        Label email = new Label("Email: user@example.com");

        VBox root = new VBox(15, title, avatar, name, email);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);

        stage.showAndWait();
    }
}
