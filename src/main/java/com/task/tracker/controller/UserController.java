package com.task.tracker.controller;

import com.task.tracker.model.Task;
import com.task.tracker.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @RequestMapping("/createUser")
    public User createUser(@RequestParam(value = "firstName") String firstName,
                           @RequestParam(value = "lastName") String lastName,
                           @RequestParam(value = "email") String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setStatus("created");

        createUserInDB(user);
        return user;
    }

    @RequestMapping("/login")
    public Object login(@RequestParam(value = "firstName") String firstName,
                      @RequestParam(value = "lastName") String lastName) {
        Session session = createSessionForDB();
        session.beginTransaction();
        Query query = session.createQuery("from User where firstName = :firstName and lastName = :lastName");
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        User user = (User) query.uniqueResult();
        if(user != null){
            return user;
        }
        return "{user doesn`t exist}";
    }

    @RequestMapping("/editUser")
    public User editUser(@RequestParam(value = "firstName") String firstName,
                         @RequestParam(value = "lastName") String lastName,
                         @RequestParam(value = "email") String email,
                         @RequestParam(value = "userId") int userId) {
        Session session = createSessionForDB();
        session.beginTransaction();
        User user = (User) session.get(User.class, userId);
        user.setStatus("updated");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        session.update(user);
        session.getTransaction().commit();
        return user;
    }

    @RequestMapping("/deleteUser")
    public User deleteUser(@RequestParam(value = "id") int id) {
        Session session = createSessionForDB();
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        user.setStatus("deleted");
        session.delete(user);
        session.getTransaction().commit();
        return user;
    }

    @RequestMapping("/getUserData")
    public User getUserData(@RequestParam(value = "id") int id) {
        Session session = createSessionForDB();
        session.beginTransaction();
        return (User) session.get(User.class, id);
    }

    @RequestMapping("/createTask")
    public Task createTask(@RequestParam(value = "title") String title,
                           @RequestParam(value = "description") String description,
                           @RequestParam(value = "status") String status,
                           @RequestParam(value = "userId") int userId) {
        Session session = createSessionForDB();
        session.beginTransaction();
        User user = (User) session.get(User.class, userId);
        Task task = new Task(title, description, status);
        user.addTask(task);
        session.save(task);
        session.getTransaction().commit();
        return task;
    }

    @RequestMapping("/updateTask")
    public Task updateTask(@RequestParam(value = "title") String title,
                           @RequestParam(value = "description") String description,
                           @RequestParam(value = "status") String status,
                           @RequestParam(value = "taskId") int taskId) {
        Session session = createSessionForDB();
        session.beginTransaction();
        Task task = (Task) session.get(Task.class, taskId);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        session.update(task);
        session.getTransaction().commit();
        return task;
    }

    @RequestMapping("/deleteTask")
    public String deleteTask(@RequestParam(value = "taskId") int taskId) {
        Session session = createSessionForDB();
        session.beginTransaction();
        Task task = (Task) session.get(Task.class, taskId);
        session.delete(task);
        session.getTransaction().commit();
        return "{\"deleted\"}";
    }

    @RequestMapping("/changeTaskStatus")
    public Object changeTaskStatus(@RequestParam(value = "status") String status,
                                   @RequestParam(value = "taskId") int taskId) {
        Session session = createSessionForDB();
        session.beginTransaction();
        Task task = (Task) session.get(Task.class, taskId);
        if (getStatusList().contains(status)) {
            task.setStatus(status);
            session.update(task);
            session.getTransaction().commit();
        } else {
            return "{\n" +
                    "  \"AllowedStatus: View, In Progress, Done\"" +
                    "}";
        }
        return task;
    }

    public List<String> getStatusList(){
        List<String> statusList = new ArrayList<>();
        statusList.add("View");
        statusList.add("In Progress");
        statusList.add("Done");
        return statusList;
    }

    @RequestMapping("/filterTaskByStatus")
    public List<Task> filterTaskByStatus(@RequestParam(value = "status") String status) {
        Session session = createSessionForDB();
        session.beginTransaction();
        Query query = session.createQuery("from Task where status = :status");
        query.setParameter("status", status);
        return (List<Task>) query.list();
    }

//    @RequestMapping("/sortTaskByUsers")
//    public List<Task> filterTaskByUsers(@RequestParam(value = "criteria") String criteria) {
//        Session session = createSessionForDB();
//        session.beginTransaction();
//        if(criteria.equalsIgnoreCase("new")){
//
//        }
//        Query query = session.createQuery("from Task where status = :status");
//        query.setParameter("status", status);
//        return (List<Task>) query.list();
//    }

    @RequestMapping("/switchTaskToAnotherUser")
    public Task switchTaskToAnotherUser(@RequestParam(value = "taskId") int taskId,
                                        @RequestParam(value = "userId") int userId) {
        Session session = createSessionForDB();
        session.beginTransaction();
        Task task = (Task) session.get(Task.class, taskId);
        User user = (User) session.get(User.class, userId);
        user.addTask(task);
        session.update(task);
        session.getTransaction().commit();
        return task;
    }

    public void createUserInDB(User user) {
        Session session = createSessionForDB();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    public Session createSessionForDB() {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        return factory.getCurrentSession();
    }

}
