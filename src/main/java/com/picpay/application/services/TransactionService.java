package com.picpay.application.services;

import com.picpay.core.domain.transaction.Transaction;
import com.picpay.core.domain.user.User;
import com.picpay.adapters.dtos.TransactionDto;
import com.picpay.adapters.dtos.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    private void validateTransaction(User payer, BigDecimal value) throws Exception {
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

    private boolean authorizeTransaction(User payer, User payee) throws Exception {
        ResponseEntity<Map> response = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
        if (HttpStatus.OK.equals(response.getStatusCode())) {
            String message = (String) response.getBody().get("message");
            String autorizado = "Autorizado";

            if (!autorizado.equals(message)) {
                throw new Exception("Transação não autorizada");
            }
        }
        throw new Exception("Erro na requisição da autorização");
    }

    public Transaction createTransaction(TransactionDto transactionDto) throws Exception {
        User payer = userService.findUserById(transactionDto.payerId());
        User payee = userService.findUserById(transactionDto.payeeId());
        BigDecimal value = transactionDto.value();

        validateTransaction(payer, value);
        authorizeTransaction(payer, payee);

        Transaction transaction = new Transaction();
        transaction.setPayer(payer);
        transaction.setPayee(payee);
        transaction.setValue(value);

        updateUserBalance(payer, payee, value);

        notificationService.send(payer.getEmail(), "Transação realizada com sucesso");
        notificationService.send(payee.getEmail(), "Transação recebida com sucesso");

        return repository.save(transaction);
    }

    public void updateUserBalance(User payer, User payee, BigDecimal value) {
        payer.setBalance(payer.getBalance().subtract(value));
        payee.setBalance(payer.getBalance().add(value));

        userService.save(payer);
        userService.save(payee);
    }
}
