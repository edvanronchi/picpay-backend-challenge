package com.picpay.application.controllers;

import com.picpay.domain.entities.user.User;
import com.picpay.application.dtos.UserDto;
import com.picpay.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Page<User>> findAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {

        Page<User> usersPage = service.findAll(page, size);
        return new ResponseEntity<>(usersPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) throws Exception {
        User user = service.save(userDto.toEntity());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
