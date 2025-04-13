package uni.usic.taskmanager.views.common;

import javafx.scene.control.*;
import javafx.stage.Stage;
import uni.usic.application.service.tasks.TaskManager;
import uni.usic.domain.entity.tasks.enums.TaskType;

import java.util.Set;

public class MainMenuBar {
    public static MenuBar create(Stage stage,
                                 Runnable showAll,
                                 Runnable showProfile,
                                 Runnable showCompleted,
                                 Runnable showUpcoming,
                                 TaskManager taskManager) {
        MenuBar menuBar = new MenuBar();

        // View
        Menu viewMenu = new Menu("Tasks");

        MenuItem allTasks = new MenuItem("All Tasks");
        allTasks.setOnAction(e -> showAll.run());

        MenuItem completedTasks = new MenuItem("Completed");
        completedTasks.setOnAction(e -> showCompleted.run());

        MenuItem upcomingTasks = new MenuItem("Upcoming");
        upcomingTasks.setOnAction(e -> showUpcoming.run());

        MenuItem taskOverview = new MenuItem("Task Overview");
        taskOverview.setOnAction(e -> showTaskOverview(taskManager));

        viewMenu.getItems().addAll(allTasks, completedTasks, upcomingTasks, taskOverview);

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

        // All menus
        menuBar.getMenus().addAll(viewMenu, accountMenu, helpMenu);

        return menuBar;
    }

    private static void showTaskOverview(TaskManager taskManager) {
        Set<TaskType> types = taskManager.getUniqueTaskTypes();
        StringBuilder sb = new StringBuilder("You've used the following task types:\n\n");
        types.forEach(type -> sb.append("• ").append(type).append("\n"));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Your Task Types");
        alert.setHeaderText(null);
        alert.setContentText(sb.toString());
        alert.showAndWait();
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
        alert.setHeaderText("Using the Task Manager");

        StringBuilder sb = new StringBuilder();
        sb.append("Here's how you can use this application:\n\n")
                .append("• Use the 'View' menu to filter your tasks (All, Completed, Upcoming).\n")
                .append("• Double-click on a task to see its details or modify it.\n")
                .append("• Click '+ Create Task' to add a new task.\n")
                .append("• Use the 'Account' menu to view your profile or logout.\n\n")
                .append("You can create tasks of the following types:\n");

        TaskType[] allTypes = TaskType.values();
        for (TaskType type : allTypes) {
            sb.append("- ").append(type.name()).append("\n");
        }

        alert.setContentText(sb.toString());
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
