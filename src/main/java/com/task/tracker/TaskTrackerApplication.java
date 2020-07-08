package com.task.tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TaskTrackerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
