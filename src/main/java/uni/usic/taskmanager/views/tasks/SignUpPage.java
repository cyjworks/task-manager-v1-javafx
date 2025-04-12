package uni.usic.taskmanager.views.tasks;

import javafx.application.Platform;
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

        Label passwordHint = new Label("Password must be at least 6 characters.");
        passwordHint.setStyle("-fx-font-size: 11; -fx-text-fill: #888888;");
        passwordHint.setVisible(false);
        passwordHint.setManaged(false);

        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            boolean showHint = newVal.length() > 0 && newVal.length() < 6;
            passwordHint.setVisible(showHint);
            passwordHint.setManaged(showHint);
        });

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        int fieldWidth = 250;
        usernameField.setMaxWidth(fieldWidth);
        passwordField.setMaxWidth(fieldWidth);
        confirmPasswordField.setMaxWidth(fieldWidth);
        fullNameField.setMaxWidth(fieldWidth);
        emailField.setMaxWidth(fieldWidth);

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Sign In");

        registerButton.setOnAction(e -> {
            handleSignUp(
                    primaryStage,
                    usernameField.getText(),
                    passwordField.getText(),
                    confirmPasswordField.getText(),
                    fullNameField.getText(),
                    emailField.getText()
            );
        });

        backButton.setOnAction(e -> {
            TaskApplication app = new TaskApplication();
            try {
                app.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(10, titleLabel, usernameField, passwordField, passwordHint, confirmPasswordField, fullNameField, emailField, registerButton, backButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        Platform.runLater(() -> root.requestFocus());

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Sign Up");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static boolean checkPasswordMatch(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }

    private static void handleSignUp(Stage primaryStage,
                                     String username,
                                     String password,
                                     String confirmPassword,
                                     String fullName,
                                     String email) {
        if (username == null || username.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Username is required.");
            return;
        }

        if (password == null || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Password is required.");
            return;
        }

        if (password.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Password must be at least 6 characters.");
            return;
        }

        if (!checkPasswordMatch(password, confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Passwords do not match.");
            return;
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Full name is required.");
            return;
        }

        if (email == null || email.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Email is required.");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Invalid email format.");
            return;
        }

        SignUpResult result = userManager.signUp(username, password, fullName, email);

        if (result == SignUpResult.SUCCESS) {
            showAlert(Alert.AlertType.INFORMATION, "Sign Up Successful", "Account created successfully! Please sign in.");
            TaskApplication app = new TaskApplication();
            try {
                app.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            String errorMessage = switch (result) {
                case USERNAME_REQUIRED -> "Username is required.";
                case PASSWORD_REQUIRED -> "Password is required.";
                case FULLNAME_REQUIRED -> "Full name is required.";
                case EMAIL_REQUIRED -> "Email is required.";
                case USERNAME_ALREADY_EXISTS -> "Username already exists.";
                default -> "An error occurred. Please try again.";
            };
            showAlert(Alert.AlertType.ERROR, "Sign Up Failed", errorMessage);
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }

    private static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
