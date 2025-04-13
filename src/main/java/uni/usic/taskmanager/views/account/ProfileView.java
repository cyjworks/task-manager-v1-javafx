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
import uni.usic.domain.entity.users.User;

public class ProfileView {
    /**
     * Shows user profile popup with actual user info.
     * @param owner parent stage
     * @param user  current logged-in user
     */
    public static void show(Stage owner, User user) {
        Stage stage = new Stage();
        stage.setTitle("User Profile");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);

        Label title = new Label("User Profile");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ImageView avatar = new ImageView(new Image("file:src/main/resources/images/profile_" + user.getUsername() + ".png"));
        avatar.setFitWidth(120);
        avatar.setFitHeight(120);

        Label username = new Label("Username: " + user.getUsername());
        Label fullName = new Label("Full Name: " + user.getFullName());
        Label email = new Label("Email: " + user.getEmail());

        VBox root = new VBox(15, title, avatar, username, fullName, email);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);

        stage.showAndWait();
    }
}
