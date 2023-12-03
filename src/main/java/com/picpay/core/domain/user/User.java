package com.picpay.core.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="USERS")
@SequenceGenerator(name = "SEQ_USERS", sequenceName = "SEQ_USERS", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USERS")
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @Column(name = "DOCUMENT", unique = true, nullable = false)
    private String document;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private UserType userType;

    public boolean isShopKeeperUser() {
        return UserType.SHOPKEEPER.equals(this.userType);
    }
}
