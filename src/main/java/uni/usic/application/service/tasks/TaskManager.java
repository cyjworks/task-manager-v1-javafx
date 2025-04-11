package uni.usic.application.service.tasks;

import uni.usic.domain.entity.tasks.Task;
import uni.usic.domain.entity.tasks.enums.TaskType;
import uni.usic.domain.entity.tasks.enums.TaskPriority;
import uni.usic.domain.entity.tasks.enums.TaskProgress;
import uni.usic.infrastructure.repository.tasks.TaskFileRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private final TaskService taskService;
    private final TaskFileRepository taskFileRepository;

    public TaskManager(TaskService taskService, TaskFileRepository taskFileRepository) {
        this.taskService = taskService;
        this.taskFileRepository = taskFileRepository;
    }

    public List<Task> viewTaskList() {
        return taskFileRepository.loadTaskListFromFile();
    }

//    public void viewTask(String id) {
//        taskFileRepository.findById(id);
//    }

    public Task viewTaskById(String id) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile();
        return taskService.viewTaskById(id, taskMap);
    }

    public Task createTask(TaskType type, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        Task task = taskService.createTask(type, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        boolean result = taskFileRepository.save(task);

        if(result) {
            return task;
        } else {
            return null;
        }
    }

    public Task modifyTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile();
        Task taskToModify = taskService.getTaskById(id, taskMap);
        if(taskToModify==null) {
            System.out.println("No task has found like task ID: " + id);
            return null;
        }

        Task modifiedTask = taskService.modifyTask(taskToModify, id, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        boolean result = taskFileRepository.update(modifiedTask);

        if(result) {
            return modifiedTask;
        } else {
            return null;
        }
    }

    public boolean checkIfTaskExists(String id) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile();
        return taskService.getTaskById(id, taskMap) != null;
    }

    public Task updateProgress(String id, TaskProgress progress) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile();
        Task taskToModify = taskService.getTaskById(id, taskMap);
        if(taskToModify==null) {
            System.out.println("No task has found like task ID: " + id);
            return null;
        }

        Task updatedTask = taskService.updateProgress(taskToModify, id,  progress);
        boolean result = taskFileRepository.update(updatedTask);

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

        return taskFileRepository.deleteById(id);
    }
}
