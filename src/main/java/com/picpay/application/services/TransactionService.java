package com.picpay.application.services;

import com.picpay.application.dtos.TransactionDto;
import com.picpay.domain.entities.transaction.Transaction;
import com.picpay.domain.entities.user.User;
import com.picpay.domain.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private NotificationService notificationService;

    private void validateTransaction(User payer, User payee, BigDecimal value) throws Exception {
        if (Objects.isNull(payer)) {
            throw new Exception("Usuário pagador não encontrado");
        }

        if (Objects.isNull(payee)) {
            throw new Exception("Usuário beneficiário não encontrado");
        }

        if (payer.isShopKeeperUser()) {
            throw new Exception("Usuários do tipo 'lojista' não possuem autorização para realizar transações");
        }

        if (payer.getBalance().compareTo(value) < 0) {
            throw new Exception("Usuário com saldo insuficiente para esta transação");
        }

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("O valor da transação deve ser superior a zero");
        }
    }

    @Transactional
    public Transaction createTransaction(TransactionDto transactionDto) throws Exception {
        User payer = userService.findUserById(transactionDto.payer());
        User payee = userService.findUserById(transactionDto.payee());
        BigDecimal value = transactionDto.value();

        validateTransaction(payer, payee, value);
        authorizationService.authorizateTransaction(payer, payee, value);

        Transaction transaction = new Transaction();
        transaction.setPayer(payer);
        transaction.setPayee(payee);
        transaction.setValue(value);

        updateUserBalance(payer, payee, value);

        notificationService.sendEmail(payer.getEmail(), "Transação realizada com sucesso!");
        notificationService.sendEmail(payee.getEmail(), "Transação recebida com sucesso!");

        return repository.save(transaction);
    }

    public void updateUserBalance(User payer, User payee, BigDecimal value) throws Exception {
        payer.setBalance(payer.getBalance().subtract(value));
        payee.setBalance(payee.getBalance().add(value));

        userService.save(payer);
        userService.save(payee);
    }
}
