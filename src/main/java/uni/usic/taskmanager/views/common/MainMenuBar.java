package uni.usic.taskmanager.views.common;

import javafx.scene.control.*;
import javafx.stage.Stage;

public class MainMenuBar {
    public static MenuBar create(Stage stage,
                                 Runnable showAll,
                                 Runnable showProfile,
                                 Runnable showCompleted,
                                 Runnable showUpcoming) {
        MenuBar menuBar = new MenuBar();

        // File (disabled)
//        Menu fileMenu = new Menu("File");
//        fileMenu.setDisable(true);

        // View
        Menu viewMenu = new Menu("View");

        MenuItem allTasks = new MenuItem("All Tasks");
        allTasks.setOnAction(e -> showAll.run());

        MenuItem completedTasks = new MenuItem("Completed");
        completedTasks.setOnAction(e -> showCompleted.run());

        MenuItem upcomingTasks = new MenuItem("Upcoming");
        upcomingTasks.setOnAction(e -> showUpcoming.run());

        viewMenu.getItems().addAll(allTasks, completedTasks, upcomingTasks);

        // Account
        Menu accountMenu = new Menu("Account");
        MenuItem profileItem = new MenuItem("Profile");
        profileItem.setOnAction(e -> showProfile.run());
        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.setOnAction(e -> handleLogoutRequest(stage));
        accountMenu.getItems().addAll(profileItem, logoutItem);

        // Help
        Menu helpMenu = new Menu("Help");
        MenuItem howToUseItem = new MenuItem("How to Use");
        howToUseItem.setOnAction(e -> showHelp());
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> showAbout());
        helpMenu.getItems().addAll(howToUseItem, aboutItem);

        menuBar.getMenus().addAll(viewMenu, accountMenu, helpMenu);

        return menuBar;
    }

    private static void handleLogoutRequest(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                stage.close();
            }
        });
    }

    private static void showHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to Use");
        alert.setHeaderText(null);
        alert.setContentText("Use the menu or double-click on a task to view/edit.");
        alert.showAndWait();
    }

    private static void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Task Manager v1.0");
        alert.setContentText("Built with JavaFX — Developed by cyj");
        alert.showAndWait();
    }
}
