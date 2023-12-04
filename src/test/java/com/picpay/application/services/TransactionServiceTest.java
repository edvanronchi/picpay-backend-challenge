package com.picpay.application.services;

import com.picpay.application.dtos.TransactionDto;
import com.picpay.domain.entities.user.User;
import com.picpay.domain.entities.user.UserType;
import com.picpay.domain.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTest {

    @MockBean
    private UserService userService;

    @MockBean
    private TransactionRepository repository;

    @MockBean
    private AuthorizationService authorizationService;

    @MockBean
    private NotificationService notificationService;

    @InjectMocks
    private TransactionService service;

    @Test
    @DisplayName("Should create a transaction")
    public void shouldCreateTransaction() throws Exception {
        User payer = createUserCommon(new BigDecimal(10));
        User payee = createUserShopkeeper(new BigDecimal(10));

        when(userService.findUserById(1L)).thenReturn(payer);
        when(userService.findUserById(2L)).thenReturn(payee);

        TransactionDto transactionDto = new TransactionDto(1L, 2L, new BigDecimal(10));
        service.createTransaction(transactionDto);

        verify(repository, times(1)).save(any());

        payer.setBalance(new BigDecimal(0));
        verify(userService, times(1)).save(payer);

        payee.setBalance(new BigDecimal(20));
        verify(userService, times(1)).save(payee);
    }

    @Test
    @DisplayName("Should validate if the paying user has a balance in the account")
    public void shouldNotCreateTransaction1() {
        User payer = createUserCommon(new BigDecimal(0));
        User payee = createUserShopkeeper(new BigDecimal(10));

        when(userService.findUserById(1L)).thenReturn(payer);
        when(userService.findUserById(2L)).thenReturn(payee);

        Exception thrown = Assertions.assertThrows(Exception.class, () ->{
            TransactionDto transactionDto = new TransactionDto(1L, 2L, new BigDecimal(10));
            service.createTransaction(transactionDto);
        });

        Assertions.assertEquals("Usuário com saldo insuficiente para esta transação", thrown.getMessage());
    }

    @Test
    @DisplayName("Should validate if the paying user is not a shopkeeper")
    public void shouldNotCreateTransaction2() {
        User payer = createUserShopkeeper(new BigDecimal(10));
        User payee = createUserCommon(new BigDecimal(10));

        when(userService.findUserById(1L)).thenReturn(payer);
        when(userService.findUserById(2L)).thenReturn(payee);

        Exception thrown = Assertions.assertThrows(Exception.class, () ->{
            TransactionDto transactionDto = new TransactionDto(1L, 2L, new BigDecimal(10));
            service.createTransaction(transactionDto);
        });

        Assertions.assertEquals("Usuários do tipo 'lojista' não possuem autorização para realizar transações", thrown.getMessage());
    }

    private User createUserCommon(BigDecimal balance) {
        return new User(1L, "João", "Silva", "cr7melhordoquemessi", balance, "82051020078", "joao@picpay.com", UserType.COMMON);
    }

    private User createUserShopkeeper(BigDecimal balance) {
        return new User(2L, "Maria", "Julia", "euamomeugato123", balance, "91601632088", "maria@picpay.com", UserType.SHOPKEEPER);
    }
}
