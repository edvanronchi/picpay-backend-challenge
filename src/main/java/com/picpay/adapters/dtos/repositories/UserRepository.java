package com.picpay.adapters.dtos.repositories;

import com.picpay.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByDocument(String document);
    User findUserById(Long id);
}
