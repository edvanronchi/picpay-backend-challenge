package com.picpay.application.dtos;

import java.math.BigDecimal;

public record TransactionDto(Long payer, Long payee, BigDecimal value) {
}
