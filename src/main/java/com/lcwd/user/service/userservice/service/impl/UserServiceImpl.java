package com.lcwd.user.service.userservice.service.impl;

import com.lcwd.user.service.userservice.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.userservice.entities.User;
import com.lcwd.user.service.userservice.repositories.UserRepository;
import com.lcwd.user.service.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    // to save data in database, we need repository
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        // generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given ID "+userId+" is not found on server! : "));
    }
}
