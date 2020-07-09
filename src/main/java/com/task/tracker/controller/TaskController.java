package com.task.tracker.controller;

import com.task.tracker.model.Task;
import com.task.tracker.model.User;
import com.task.tracker.service.TaskService;
import com.task.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @RequestMapping("/createTask")
    public Task createTask(@RequestParam(value = "title") String title,
                           @RequestParam(value = "description") String description,
                           @RequestParam(value = "status") String status,
                           @RequestParam(value = "userId") int userId) {
        Task task = new Task(title, description, status);
        userService.getUser(userId).addTask(task);
        taskService.saveTask(task);
        return task;
    }

    @RequestMapping("/updateTask")
    public Task updateTask(@RequestParam(value = "title") String title,
                           @RequestParam(value = "description") String description,
                           @RequestParam(value = "status") String status,
                           @RequestParam(value = "taskId") int taskId) {
        Task task = taskService.getTask(taskId);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        taskService.saveTask(task);
        return task;
    }

    @RequestMapping("/changeTaskStatus")
    public Object changeTaskStatus(@RequestParam(value = "status") String status,
                                   @RequestParam(value = "taskId") int taskId) {
        Task task = taskService.getTask(taskId);
        if (getStatusList().contains(status)) {
            task.setStatus(status);
            taskService.saveTask(task);
        } else {
            return "{\n" +
                    "  \"AllowedStatus: View, In Progress, Done\"" +
                    "}";
        }
        return task;
    }

    @RequestMapping("/deleteTask")
    public String deleteTask(@RequestParam(value = "taskId") int taskId) {
        taskService.deleteTask(taskService.getTask(taskId));
        return "{\"deleted\"}";
    }

    @RequestMapping("/filterTaskByStatus")
    public List<Task> filterTaskByStatus(@RequestParam(value = "status") String status) {
        return taskService.filterTheTasksByStatus(status);
    }

    @RequestMapping("/sortTaskByUsers")
    public List<Task> filterTaskByUsers(@RequestParam(value = "criteria") String criteria) {
        return taskService.sortTasksByUsers(criteria);
    }

    @RequestMapping("/switchTaskToAnotherUser")
    public Task switchTaskToAnotherUser(@RequestParam(value = "taskId") int taskId,
                                        @RequestParam(value = "userId") int userId) {
        Task task = taskService.getTask(taskId);
        User user = userService.getUser(userId);
        user.addTask(task);
        taskService.saveTask(task);
        return task;
    }

    public List<String> getStatusList() {
        List<String> statusList = new ArrayList<>();
        statusList.add("View");
        statusList.add("In Progress");
        statusList.add("Done");
        return statusList;
    }
}
