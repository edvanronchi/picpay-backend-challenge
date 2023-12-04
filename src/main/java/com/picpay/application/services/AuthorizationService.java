package com.picpay.application.services;

import com.picpay.application.dtos.ValidadeTransactionDto;
import com.picpay.domain.entities.user.User;
import com.picpay.infra.ControllerExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AuthorizationService {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    public void authorizateTransaction(User payer, User payee, BigDecimal value) throws Exception {
        ValidadeTransactionDto validadeTransaction = new ValidadeTransactionDto(payer, payee, value);
        ResponseEntity<Map> response = restTemplate.postForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", validadeTransaction, Map.class);
        if (HttpStatus.OK.equals(response.getStatusCode())) {
            String responseMessage = (String) response.getBody().get("message");
            boolean isAuthorized = "Autorizado".equals(responseMessage);

            if (isAuthorized) {
                logger.info("Transação autorizada");
                return;
            }

            throw new Exception("Transação não autorizada");
        }
        throw new Exception("Erro na requisição da autorização");
    }
}
