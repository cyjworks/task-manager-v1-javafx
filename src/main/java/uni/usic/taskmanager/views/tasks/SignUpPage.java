package uni.usic.taskmanager.views.tasks;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import uni.usic.application.service.users.UserManager;
import uni.usic.application.service.users.UserService;
import uni.usic.domain.entity.users.enums.SignUpResult;
import uni.usic.infrastructure.repository.users.UserFileRepository;
import uni.usic.taskmanager.TaskApplication;

public class SignUpPage {
    private static final String USERS_FILE_PATH = "src/main/java/uni/usic/infrastructure/database/users.txt";
    private static final UserManager userManager = new UserManager(
            new UserService(new UserFileRepository(USERS_FILE_PATH)));

    public static void show(Stage primaryStage) {
        Label titleLabel = new Label("Sign Up");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        int fieldWidth = 250;
        usernameField.setMaxWidth(fieldWidth);
        passwordField.setMaxWidth(fieldWidth);
        fullNameField.setMaxWidth(fieldWidth);
        emailField.setMaxWidth(fieldWidth);

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Sign In");

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String fullName = fullNameField.getText();
            String email = emailField.getText();

            SignUpResult result = userManager.signUp(username, password, fullName, email);

            if (result == SignUpResult.SUCCESS) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sign Up Successful");
                alert.setContentText("Account created successfully! Please sign in.");
                alert.showAndWait();
                TaskApplication app = new TaskApplication();
                try {
                    app.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sign Up Failed");
                alert.setContentText(switch (result) {
                    case USERNAME_REQUIRED -> "Username is required.";
                    case PASSWORD_REQUIRED -> "Password is required.";
                    case FULLNAME_REQUIRED -> "Full name is required.";
                    case EMAIL_REQUIRED -> "Email is required.";
                    case USERNAME_ALREADY_EXISTS -> "Username already exists.";
                    default -> "An error occurred. Please try again.";
                });
                alert.showAndWait();
            }
        });

        backButton.setOnAction(e -> {
            TaskApplication app = new TaskApplication();
            try {
                app.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(10, titleLabel, usernameField, passwordField, fullNameField, emailField, registerButton, backButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Sign Up");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
