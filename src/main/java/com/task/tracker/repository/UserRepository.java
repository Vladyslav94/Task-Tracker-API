package com.task.tracker.repository;

import com.task.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("from User where firstName = :firstName and lastName = :lastName")
    User findUserByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

}