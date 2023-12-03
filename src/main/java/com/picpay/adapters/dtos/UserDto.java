package com.picpay.adapters.dtos;

import com.picpay.core.domain.user.User;
import com.picpay.core.domain.user.UserType;

import java.math.BigDecimal;

public record UserDto(Long id, String firstName, String lastName, String document, String email, BigDecimal balance, String password, UserType userType) {
    public User toEntity() {
        User user = new User();
        user.setId(id);
        user.setBalance(balance);
        user.setDocument(document);
        user.setEmail(email);
        user.setUserType(userType);
        user.setPassword(password);
        return user;
    }

    public UserDto fromEntity(User user) {
        UserDto userDto = new UserDto();
        return new UserDto(user.getId(), product.getPrice());
    }
}
