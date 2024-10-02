package com.lcwd.user.service.userservice.service.impl;

import com.lcwd.user.service.userservice.entities.Hotel;
import com.lcwd.user.service.userservice.entities.Rating;
import com.lcwd.user.service.userservice.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.userservice.entities.User;
import com.lcwd.user.service.userservice.external.services.HotelService;
import com.lcwd.user.service.userservice.repositories.UserRepository;
import com.lcwd.user.service.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    // to save data in database, we need repository
    private UserRepository userRepository;

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;


    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    // construction injection is mandatory
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public User saveUser(User user) {
        // generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        // implement RATING SERVICE to get ratings of all users, using RestTemplate
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        // get user from database with the help of user repository
        User user =  userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given ID "+userId+" is not found on server! : "));

        // fetch rating of the above user from RATING SERVICE
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList= ratings.stream().map(rating -> {
            // api call to hotel service to get hotel details
            // http://localhost:8082/hotels/26c81fda-bb86-420b-9dbe-c86dd740710e

            // using RestTemplate
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+ rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();

            // using FeignClient
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            // set the hotel to rating
            rating.setHotel(hotel);


            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return user;
    }

//    @Override
//    public User deleteUser(String userId) {
//        // fetch user before deleting it to show which user is deleted
//        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
//
//        // deleting the user
//        userRepository.deleteById(userId);
//
//        // returning that user
//        return user;
//    }
}
