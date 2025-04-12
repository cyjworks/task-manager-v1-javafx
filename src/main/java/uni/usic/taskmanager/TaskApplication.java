package uni.usic.taskmanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import uni.usic.application.service.users.UserManager;
import uni.usic.application.service.users.UserService;
import uni.usic.domain.entity.users.enums.SignInResult;
import uni.usic.infrastructure.repository.users.UserFileRepository;
import uni.usic.taskmanager.views.tasks.SignUpPage;
import uni.usic.taskmanager.views.tasks.TaskList;

import java.io.IOException;

public class TaskApplication extends Application {
    private UserManager userManager;
    @Override
    public void start(Stage primaryStage) throws IOException {
        String userFilePath = "src/main/java/uni/usic/infrastructure/database/users.txt";

        UserFileRepository userRepository = new UserFileRepository(userFilePath);
        UserService userService = new UserService(userRepository);
        userManager = new UserManager(userService);

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

        Button signInButton = new Button("Sign In");

        signInButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            SignInResult result = userManager.signIn(username, password);

            if (result == SignInResult.SUCCESS) {
                TaskList taskListScreen = new TaskList();
                taskListScreen.show(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText(switch (result) {
                    case USERNAME_REQUIRED -> "Username is required.";
                    case PASSWORD_REQUIRED -> "Password is required.";
                    case USER_NOT_FOUND -> "User not found.";
                    case WRONG_PASSWORD -> "Incorrect password.";
                    default -> "Unknown error.";
                });
                alert.showAndWait();
            }
        });

        HBox signUpBox = new HBox();
        Label signUpPrompt = new Label("Don't have an account?");
        Button signUpButton = new Button("Sign Up");
        signUpBox.getChildren().addAll(signUpPrompt, signUpButton);
        signUpBox.setAlignment(Pos.CENTER);
        signUpBox.setSpacing(10);

        signUpButton.setOnAction(e -> {
            SignUpPage signUpPage = new SignUpPage();
            signUpPage.show(primaryStage);
        });

        VBox root = new VBox(20, subHeaderLabel, titleLabel, usernameField, passwordField, signInButton, signUpButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        Platform.runLater(() -> root.requestFocus());

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}