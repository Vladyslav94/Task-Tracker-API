package com.task.tracker.repository;

import com.task.tracker.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryForPaging
        extends PagingAndSortingRepository<User, Integer> {

}
