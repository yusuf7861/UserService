package com.lcwd.user.service.userservice.repositories;

import com.lcwd.user.service.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // if you want, you can create your custom methods
}
