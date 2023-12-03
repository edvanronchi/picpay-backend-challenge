package com.picpay.application.services;

import com.picpay.adapters.dtos.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void send(String email, String message) {
        NotificationDto notification = new NotificationDto(email, message);
        ResponseEntity<String> response = restTemplate.postForEntity("http://o4d9z.mocklab.io/notify", notification, String);

        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            logger.warn("Serviço de notificação está indisponivel");
        }
    }
}
