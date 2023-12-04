package com.picpay.application.dtos;

import com.picpay.domain.entities.user.User;
import com.picpay.domain.entities.user.UserType;

public record UserDto(String firstName, String lastName, String document, String email, String password, UserType userType) {
    public User toEntity() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDocument(document);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(userType);
        return user;
    }
}
