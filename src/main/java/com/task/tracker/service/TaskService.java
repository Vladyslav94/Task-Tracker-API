package com.task.tracker.service;

import com.task.tracker.model.Task;
import com.task.tracker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public void saveTask(Task task){
        taskRepository.save(task);
    }

    public Task getTask(int taskId){
        return taskRepository.findById(taskId).get();
    }

    public void deleteTask(Task task){
        taskRepository.delete(task);
    }

    public List<Task> filterTheTasksByStatus(String status){
        return taskRepository.filterTaskByStatus(status);
    }

    public List<Task> sortTasksByUsers(String criteria){
        List<Task> taskList;
        if(criteria.equalsIgnoreCase("created")){
            taskList = taskRepository.sortTaskByUsersASC(criteria);
        } else {
            taskList = taskRepository.sortTaskByUsersDESC(criteria);
        }

        return taskList;
    }
}
