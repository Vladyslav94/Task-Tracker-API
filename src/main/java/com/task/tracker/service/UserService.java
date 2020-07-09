package com.task.tracker.service;

import com.task.tracker.model.User;
import com.task.tracker.repository.UserRepository;
import com.task.tracker.repository.UserRepositoryForPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryForPaging userRepo;

    public List<User> getAllEmployees(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<User> pagedResult = userRepo.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<User>();
        }
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUser(int userId){
        return userRepository.findById(userId).get();
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public User logInUser(String fistName, String lastName){
        return userRepository.findUserByFirstNameAndLastName(fistName, lastName);
    }


}
