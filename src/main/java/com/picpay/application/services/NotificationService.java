package com.picpay.application.services;

import com.picpay.application.dtos.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    public void sendEmail(String email, String message) throws Exception {
        NotificationDto notification = new NotificationDto(email, message);
        ResponseEntity<Map> response = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notification, Map.class);

        if (HttpStatus.OK.equals(response.getStatusCode())) {
            boolean responseMessage = (boolean) response.getBody().get("message");
            boolean notificationSent = Boolean.TRUE.equals(responseMessage);

            if (notificationSent) {
                logger.warn(message);
                return;
            }
            throw new Exception("Notificação não enviada");
        }
        throw new Exception("Erro na requisição de notificação");
    }
}
