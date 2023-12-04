package com.picpay.application.services;

import com.picpay.domain.entities.user.User;
import com.picpay.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Page<User> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest);
    }

    public User findUserById(Long id) {
        return repository.findUserById(id);
    }

    public User save(User user) throws Exception {
        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Já existe um usuário registrado com o mesmo endereço de e-mail ou número de documento");
        } catch (Exception e) {
            throw new Exception("Erro interno");
        }
    }
}
