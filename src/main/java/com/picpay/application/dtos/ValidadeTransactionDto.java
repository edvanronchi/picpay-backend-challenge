package com.picpay.application.dtos;

import com.picpay.domain.entities.user.User;

import java.math.BigDecimal;

public record ValidadeTransactionDto(User payer, User payee, BigDecimal value) {
}
