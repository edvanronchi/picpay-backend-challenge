package com.picpay.core.domain.transaction;

import com.picpay.core.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="TRANSACTIONS")
@SequenceGenerator(name = "SEQ_TRANSACTIONS", sequenceName = "SEQ_TRANSACTIONS", allocationSize = 1)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANSACTIONS")
    private Long id;

    @Column(name = "VALUE", nullable = false)
    private BigDecimal value;

    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "PAYER_ID", nullable = false)
    private User payer;

    @ManyToOne
    @JoinColumn(name = "PAYEE_ID", nullable = false)
    private User payee;
}
