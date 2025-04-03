package uni.usic.taskmanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class TaskApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Upper small label
        Label subHeaderLabel = new Label("This is your");
        subHeaderLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        subHeaderLabel.setPadding(new Insets(0, 0, -10, 0));

        // Main bold title
        Label titleLabel = new Label("Task Manager");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setPadding(new Insets(-10, 0, 0, 0));

//        Label titleLabel = new Label("Task Manager");
//        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 26));
//        titleLabel.setTextAlignment(TextAlignment.CENTER);

        TextField usernameField = new TextField();
        usernameField.setMaxWidth(250);
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(250);
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Sign In");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

//            if (username.equals("admin") && password.equals("1234")) {
                TaskList taskListScreen = new TaskList();
                taskListScreen.show(primaryStage);  // 화면 전환
//            }
        });

        VBox root = new VBox(20, subHeaderLabel, titleLabel, usernameField, passwordField, loginButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        Platform.runLater(() -> root.requestFocus());

        Scene scene = new Scene(root, 500, 500);
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}