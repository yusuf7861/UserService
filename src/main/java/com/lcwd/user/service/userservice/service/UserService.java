package com.lcwd.user.service.userservice.service;

import com.lcwd.user.service.userservice.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    //user operations

    // save user
    User saveUser(User user);

    // get all user 
    List<User> getAllUser();

    // get user by id
    User getUser(String userId);

    //TODO: delete user
    //TODO: update user
}
