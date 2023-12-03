package com.picpay.controllers;

import com.picpay.core.domain.user.User;
import com.picpay.adapters.dtos.UserDto;
import com.picpay.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
        User user = userService.save(userDto);
        return ResponseEntity.created(user).build();
    }
}
