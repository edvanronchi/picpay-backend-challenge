package com.picpay.domain.entities.transaction;

import com.picpay.domain.entities.user.User;
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
@Table(name="TRANSACTIONS", schema = "PICPAY")
@SequenceGenerator(name = "SEQ_TRANSACTIONS", sequenceName = "SEQ_TRANSACTIONS", allocationSize = 1, schema = "PICPAY")
public class Transaction {

    @Id
    @Column(name = "ID", updatable = false)
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
