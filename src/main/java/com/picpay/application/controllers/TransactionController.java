package com.picpay.application.controllers;

import com.picpay.application.dtos.TransactionDto;
import com.picpay.application.services.TransactionService;
import com.picpay.domain.entities.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDto transactionDto) throws Exception {
        Transaction user = service.createTransaction(transactionDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
