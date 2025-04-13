package uni.usic.infrastructure.repository.tasks;

import uni.usic.domain.entity.tasks.*;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class TaskFileRepository implements TaskRepository {
    private final String filePath;

    public TaskFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean save(Task task) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(taskToString(task));
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<Task> findAll(String ownerUsername) {
        return loadTaskListFromFile(ownerUsername);
    }

    @Override
    public Optional<Task> findById(String ownerUsername, String taskId) {
        return loadTaskListFromFile(ownerUsername).stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst();
    }

    @Override
    public boolean update(String ownerUsername, Task task) {
        List<Task> tasks = loadTaskListFromFile(ownerUsername);
        boolean updated = false;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(Task t : tasks) {
                if(t.getId().equals(task.getId())) {
                    writer.write(taskToString(task));
                    updated = true;
                } else {
                    writer.write(taskToString(t));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            return false;
        }
        return updated;
    }

    @Override
    public boolean deleteById(String ownerUsername, String taskId) {
        List<Task> tasks = loadTaskListFromFile(ownerUsername);
        boolean deleted = false;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(Task task : tasks) {
                if(!task.getId().equals(taskId)) {
                    writer.write(taskToString(task));
                    writer.newLine();
                } else {
                    deleted = true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return deleted;
    }

    @Override
    public void deleteAll(String ownerUsername) {
        try{
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Error deleting all tasks: " + e.getMessage());
        }
    }

    private static String taskToString(Task task) {
        return task.getOwnerUsername() + "|" +
                task.getId() + "|" +
                task.getType() + "|" +
                task.getTitle() + "|" +
                task.getDescription() + "|" +
                task.getStartDate() + "|" +
                task.getEndDate() + "|" +
                task.getPriority() + "|" +
                task.getProgress() + "|" +
                (task.getReminderDaysBefore() == null ? "" : task.getReminderDaysBefore());
    }

    private static Task stringToTask(String line) {
        String[] parts = line.split("\\|");

        if (parts.length < 9) return null;

        String ownerUsername = parts[0];
        String id = parts[1];
        TaskType type = TaskType.valueOf(parts[2]);
        String title = parts[3];
        String description = parts[4];
        LocalDate startDate = LocalDate.parse(parts[5]);
        LocalDate endDate = LocalDate.parse(parts[6]);
        TaskPriority priority = TaskPriority.valueOf(parts[7]);
        TaskProgress progress = TaskProgress.valueOf(parts[8]);

        Integer reminderDaysBefore = null;
        if (parts.length > 9 && parts[9] != null && !parts[9].trim().isEmpty()) {
            try {
                reminderDaysBefore = Integer.parseInt(parts[9]);
            } catch (NumberFormatException ignored) {}
        }

        Task task;
        switch (type) {
            case STUDY -> task = new StudyTask(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, null, null, 0);
            case WORK -> task = new WorkTask(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, null);
            case HABIT -> task = new HabitTask(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, 0, null);
            case GOAL -> task = new GoalTask(ownerUsername, id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, null, 0);
            default -> throw new IllegalArgumentException("Unknown task type: " + type);
        }
        return task;
    }

    public List<Task> loadTaskListFromFile(String ownerUsername) {
        List<Task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = stringToTask(line);
                if (task != null && task.getOwnerUsername().equals(ownerUsername)) {
                    tasks.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No tasks file found. Creating a new one...");
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    public Map<String, Task> loadTaskMapFromFile(String ownerUsername) {
        Map<String, Task> taskMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = stringToTask(line);
                if (task != null && task.getOwnerUsername().equals(ownerUsername)) {
                    taskMap.put(task.getId(), task);
                }
            }
        } catch (IOException e) {
            System.out.println("No previous tasks found or error loading file.");
        }
        return taskMap;
    }

}
