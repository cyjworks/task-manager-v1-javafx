package uni.usic.application.service.tasks;

import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.infrastructure.repository.tasks.TaskFileRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaskManager {
    private String ownerUsername;
    private final TaskService taskService;
    private final TaskFileRepository taskFileRepository;

    public TaskManager(String ownerUsername, TaskService taskService, TaskFileRepository taskFileRepository) {
        this.ownerUsername = ownerUsername;
        this.taskService = taskService;
        this.taskFileRepository = taskFileRepository;
    }

    public List<Task> viewTaskList() {
        return taskFileRepository.loadTaskListFromFile(ownerUsername);
    }

//    public void viewTask(String id) {
//        taskFileRepository.findById(id);
//    }

    public Task viewTaskById(String id) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile(ownerUsername);
        return taskService.viewTaskById(id, taskMap);
    }

    public Set<TaskType> getUniqueTaskTypes() {
        Set<TaskType> types = new HashSet<>();
        for (Task task : viewTaskList()) {
            types.add(task.getType());
        }
        return types;
    }


    public Task createTask(TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        Task task = taskService.createTask(ownerUsername, type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        boolean result = taskFileRepository.save(task);

        if(result) {
            return task;
        } else {
            return null;
        }
    }

    public Task modifyTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile(ownerUsername);
        Task taskToModify = taskService.getTaskById(id, taskMap);
        if(taskToModify==null) {
            System.out.println("No task has found like task ID: " + id);
            return null;
        }

        Task modifiedTask = taskService.modifyTask(ownerUsername, taskToModify, id, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        boolean result = taskFileRepository.update(ownerUsername, modifiedTask);

        if(result) {
            return modifiedTask;
        } else {
            return null;
        }
    }

    public boolean checkIfTaskExists(String id) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile(ownerUsername);
        return taskService.getTaskById(id, taskMap) != null;
    }

    public Task updateProgress(String id, TaskProgress progress) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile(ownerUsername);
        Task taskToModify = taskService.getTaskById(id, taskMap);
        if(taskToModify==null) {
            System.out.println("No task has found like task ID: " + id);
            return null;
        }

        Task updatedTask = taskService.updateProgress(ownerUsername, taskToModify, id,  progress);
        boolean result = taskFileRepository.update(ownerUsername, updatedTask);

        if(result) {
            return updatedTask;
        } else {
            return null;
        }
    }

    public boolean deleteTask(String id) {
        boolean taskExists = checkIfTaskExists(id);
        if(!taskExists) {
            System.out.println("No task has found like task ID: " + id);
            return false;
        }

        return taskFileRepository.deleteById(ownerUsername, id);
    }
}
