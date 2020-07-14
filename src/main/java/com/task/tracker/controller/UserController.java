package com.task.tracker.controller;

import com.task.tracker.service.TaskService;
import com.task.tracker.service.UserService;
import com.task.tracker.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @RequestMapping("/createUser")
    public User createUser(@RequestParam(value = "firstName") String firstName,
                           @RequestParam(value = "lastName") String lastName,
                           @RequestParam(value = "email") String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setStatus("created");
        userService.saveUser(user);
        return user;
    }

    @RequestMapping("/login")
    public Object login(@RequestParam(value = "firstName") String firstName,
                        @RequestParam(value = "lastName") String lastName) {
        User user = userService.logInUser(firstName, lastName);
        if (user != null) {
            return user;
        }
        return "{user doesn`t exist}";
    }

    @RequestMapping("/editUser")
    public User editUser(@RequestParam(value = "firstName") String firstName,
                         @RequestParam(value = "lastName") String lastName,
                         @RequestParam(value = "email") String email,
                         @RequestParam(value = "userId") int userId) {
        User user = userService.getUser(userId);
        user.setStatus("updated");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userService.saveUser(user);
        return user;
    }

    @RequestMapping("/deleteUser")
    public User deleteUser(@RequestParam(value = "id") int id) {
        User user = userService.getUser(id);
        user.setStatus("deleted");
        userService.deleteUser(user);
        return user;
    }

    @RequestMapping("/getUserData")
    public User getUserData(@RequestParam(value = "id") int id) {
        return userService.getUser(id);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<User> list = userService.getAllUsers(pageNo, pageSize, sortBy);

        return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
    }

}
