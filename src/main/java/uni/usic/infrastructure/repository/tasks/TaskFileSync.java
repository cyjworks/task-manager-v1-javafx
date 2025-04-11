package uni.usic.infrastructure.repository.tasks;

import uni.usic.application.service.tasks.TaskService;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

public class TaskFileSync {
    private static final String DIRECTORY = "uni/usic/";
    private static final String TASKS_FILE = DIRECTORY + "tasks.txt";
    private static final String TEMP_FILE = DIRECTORY + "temp.txt";

    public void initialiseAfterStart() {
        try {
            File dir = new File(DIRECTORY);
            if(!dir.exists()) {
                dir.mkdir();
            }

            File tasksFile = new File(TASKS_FILE);
            File tempFile = new File(TEMP_FILE);

            if(tasksFile.exists()) {
                Files.copy(tasksFile.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } else {
                tempFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error initialising temp file: " + e.getMessage());
        }
    }

    /*public static void saveTasksToTemp(Map<String, Task> taskMap) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(TEMP_FILE));
            for(Task task : taskMap.values()) {
                writer.write(taskToString(task));
            }
        } catch (IOException e) {
            System.out.println("Error copying tasks file to temp file: " + e.getMessage());
        }
    }*/

    public Map<String, Task> loadTasksFromTemp() {
        Map<String, Task> taskMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEMP_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = stringToTask(line);
                if (task != null) {
                    taskMap.put(task.getId(), task);
                }
            }
//            System.out.println("Tasks loaded from temp file.");
        } catch (IOException e) {
            System.out.println("No previous tasks found.");
        }
        return taskMap;
    }

    public boolean finaliseBeforeExit() {
        File tempFile = new File(TEMP_FILE);

        if(tempFile.exists() || tempFile.length()>0) {
            sortByDateAsc();
            boolean overwriteSuccess = overwriteToTasks();
            if(overwriteSuccess) {
                deleteTemp();
            }
            return true;
        }
        return false;
    }

    /*public static boolean isTempFileValid() {
        File file = new File(TEMP_FILE);
        return file.exists() && file.length() > 0;
    }*/

    public void sortByDateAsc() {
        Map<String, Task> taskMap = loadTasksFromTemp();
        TaskService taskService = new TaskService(TEMP_FILE);   // TODO: the parameter is not needed in this case
        List<Task> tasks = taskService.convertTaskMapToList(taskMap); // Load tasks from temp file
        tasks.sort(Comparator.comparing(Task::getEndDate)); // Sort in ascending order

        // Rewrite sorted tasks back to temp.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_FILE))) {
            for (Task task : tasks) {
                writer.write(taskToString(task));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error sorting temp file: " + e.getMessage());
        }
    }

    public boolean overwriteToTasks() {
        try {
            Files.copy(Paths.get(TEMP_FILE), Paths.get(TASKS_FILE), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Successfully updated tasks.txt from temp.txt.");
            return true;
        } catch (IOException e) {
            System.out.println("Error overwriting tasks.txt: " + e.getMessage());
            return false;
        }
    }

    public void deleteTemp() {
        try {
            Files.deleteIfExists(Paths.get(TEMP_FILE));
            System.out.println("temp.txt deleted successfully.");
        } catch (IOException e) {
            System.out.println("Error deleting temp.txt: " + e.getMessage());
        }
    }

    public void finaliseTasksBeforeExit() {
        try {
            // Read all tasks from temp.txt and sort them
            List<Task> sortedTasks = new ArrayList<>(loadTasksFromTemp().values());
            sortedTasks.sort(Comparator.comparing(Task::getEndDate).reversed()); // Sorting by End Date in Descending Order

            // Write sorted data back to tasks.txt
            try (PrintWriter writer = new PrintWriter(new FileWriter(TASKS_FILE))) {
                for (Task task : sortedTasks) {
                    writer.write(taskToString(task));
                }
            }

            // Delete temp.txt
            Files.deleteIfExists(Paths.get(TEMP_FILE));
            System.out.println("Tasks finalized & saved. Application exiting.");
        } catch (IOException e) {
            System.out.println("Error finalizing tasks before exit: " + e.getMessage());
        }
    }

    private Task stringToTask(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 7) return null;

        String id = parts[0];
        TaskType type = TaskType.valueOf(parts[1]);
        String title = parts[2];
        String description = parts[3];
        LocalDate startDate = LocalDate.parse(parts[4]);
        LocalDate endDate = LocalDate.parse(parts[5]);
        TaskPriority priority = TaskPriority.valueOf(parts[6]);
        TaskProgress progress = TaskProgress.valueOf(parts[7]);
        Integer reminderDaysBefore = parts[8].isEmpty() ? null : Integer.parseInt(parts[8]);

        Task task = new Task(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        return task;
    }













    public boolean saveTaskListToFile(String filePath, List<Task> taskList) {
        try {
            FileWriter writer = new FileWriter(filePath);
            for(Task task : taskList) {
                writer.write(taskToString(task) + "\n");
                return true;
            };
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
            return false;
        }
        return false;
    }

    public String taskToString(Task task) {
        return task.getId() + "|" +
                task.getTitle() + "|" +
                task.getDescription() + "|" +
                task.getStartDate() + "|" +
                task.getEndDate() + "|" +
                task.getPriority() + "|" +
                task.getProgress() + "|" +
                (task.getReminderDaysBefore() == null ? "" : task.getReminderDaysBefore());
    }
}
