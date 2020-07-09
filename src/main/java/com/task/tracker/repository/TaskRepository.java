package com.task.tracker.repository;

import com.task.tracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("from Task where status = :status")
    List<Task> filterTaskByStatus(@Param("status") String status);

    @Query("from Task f ORDER BY f.status ASC")
    List<Task> sortTaskByUsersASC(@Param("criteria") String criteria);

    @Query("from Task f ORDER BY f.status DESC")
    List<Task> sortTaskByUsersDESC(@Param("criteria") String criteria);
}
