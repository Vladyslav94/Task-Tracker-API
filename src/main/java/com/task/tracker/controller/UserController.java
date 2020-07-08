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


@RestController
public class UserController {

    @RequestMapping("/createUser")
    public User createUser(@RequestParam(value = "firstName") String firstName,
                           @RequestParam(value = "lastName") String lastName,
                           @RequestParam(value = "email") String email,
                           @RequestParam(value = "taskId") int taskId) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setTaskId(taskId);
        user.setStatus("created");

        createUserInDB(user);
        return user;
    }

    @RequestMapping("/login")
    public User login(@RequestParam(value = "firstName") String firstName,
                      @RequestParam(value = "lastName") String lastName){
        Session session = createSessionForDB();
        session.beginTransaction();
        Query query = session.createQuery("from User where firstName = :firstName and lastName = :lastName");
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return (User) query.uniqueResult();
    }

    @RequestMapping("/editUser")
    public User editUser(@RequestParam(value = "firstName") String firstName,
                           @RequestParam(value = "lastName") String lastName,
                           @RequestParam(value = "email") String email,
                           @RequestParam(value = "userId") int userId){
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
    public User deleteUser(@RequestParam(value = "id") int id){
        Session session = createSessionForDB();
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        user.setStatus("deleted");
        session.delete(user);
        session.getTransaction().commit();
        return user;
    }

    @RequestMapping("/createTask")
    public User createTask(@RequestParam(value = "id") int id){
        Session session = createSessionForDB();
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        Task task = new Task("aa", "111", "in progress");
        user.addTask(task);
        session.save(task);
        session.getTransaction().commit();
        return user;
    }

    @RequestMapping("/getUserData")
    public User getUserData(@RequestParam(value = "id") int id){
        Session session = createSessionForDB();
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        System.out.println(user.getTaskList());
        return user;
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
