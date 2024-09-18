package com.lcwd.user.service.userservice.controllers;

import com.lcwd.user.service.userservice.entities.User;
import com.lcwd.user.service.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

//    Create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        // created the user
        User user1 = userService.saveUser(user);
        // return the response that the user is created
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    // get single user
    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId)
    {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    // get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

}
