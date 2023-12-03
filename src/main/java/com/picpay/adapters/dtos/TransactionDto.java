package com.picpay.adapters.dtos;

import java.math.BigDecimal;

public record TransactionDto(BigDecimal value, Long payerId, Long payeeId) {
}
