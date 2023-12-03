package com.picpay.application.services;

import com.picpay.core.domain.user.User;
import com.picpay.adapters.dtos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;



    public User findUserById(Long id) throws Exception {
        return repository.findUserById(id);
    }

    public void save(User user) {
        repository.save(user);
    }
}
