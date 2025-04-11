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
import java.util.stream.Collectors;

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
    public List<Task> findAll() {
        return loadTaskListFromFile();
    }

    @Override
    public Optional<Task> findById(String taskId) {
        return loadTaskListFromFile().stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst();
//        return Optional.empty();
    }

    @Override
    public List<Task> findByPriority(TaskPriority priority) {
        return loadTaskListFromFile().stream()
                .filter(task -> task.getPriority().equals(priority))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByProgress(TaskProgress progress) {
        return loadTaskListFromFile().stream()
                .filter(task -> task.getProgress().equals(progress))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByDateRange(LocalDate start, LocalDate end) {
        return null;
    }

    @Override
    public boolean update(Task task) {
        List<Task> tasks = loadTaskListFromFile();
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
    public boolean deleteById(String taskId) {
        List<Task> tasks = loadTaskListFromFile();
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
    public void deleteAll() {
        try{
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Error deleting all tasks: " + e.getMessage());
        }
    }

    private static String taskToString(Task task) {
        return task.getId() + "|" +
                task.getType() + "|" +
                task.getTitle() + "|" +
                task.getDescription() + "|" +
                task.getStartDate() + "|" +
                task.getEndDate() + "|" +
                task.getPriority() + "|" +
                task.getProgress() + "|" +
                (task.getReminderDaysBefore() == null ? "" : task.getReminderDaysBefore()) + "|";
    }

    private static Task stringToTask(String line) {
        String[] parts = line.split("\\|");

        if (parts.length < 8) return null;  // Basic 8 fields: id, type, title, description, start, end, priority, progress (reminder is optional)

        String id = parts[0];
        TaskType type = TaskType.valueOf(parts[1]);
        String title = parts[2];
        String description = parts[3];
        LocalDate startDate = LocalDate.parse(parts[4]);
        LocalDate endDate = LocalDate.parse(parts[5]);
        TaskPriority priority = TaskPriority.valueOf(parts[6]);
        TaskProgress progress = TaskProgress.valueOf(parts[7]);

        // reminderDaysBefore
        Integer reminderDaysBefore = null;
        if (parts.length > 8 && parts[8] != null && !parts[8].trim().isEmpty()) {
            try {
                reminderDaysBefore = Integer.parseInt(parts[8]);
            } catch (NumberFormatException e) {
                reminderDaysBefore = null; // ignore the wrong number
            }
        }

        Task task;
        switch (type) {
            case STUDY -> task = new StudyTask(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, null, null, 0);
            case WORK -> task = new WorkTask(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, null);
            case HABIT -> task = new HabitTask(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, 0, null);
            case GOAL -> task = new GoalTask(id, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore, null, 0);
            default -> throw new IllegalArgumentException("Unknown task type: " + type);
        }

        return task;
    }

    public List<Task> loadTaskListFromFile() {
        List<Task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = stringToTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
//            System.out.println("Loaded " + tasks.size() + " tasks from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No tasks file found. Creating a new one...");
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    public Map<String, Task> loadTaskMapFromFile() {
        Map<String, Task> taskMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = stringToTask(line);
                if (task != null) {
                    taskMap.put(task.getId(), task);
                }
            }
//            System.out.println("Tasks loaded successfully.");
        } catch (IOException e) {
            System.out.println("No previous tasks found or error loading file.");
        }
        return taskMap;
    }

}
