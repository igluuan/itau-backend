package tech.devluan.itau_backend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tech.devluan.itau_backend.model.Transaction;
import tech.devluan.itau_backend.service.TransactionService;

@RestController
@RequestMapping("transacao")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
   
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        transactionService.validateTransaction(transaction);
        transactionService.createTransaction(transaction);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTransactions() {
        transactionService.deleteAllTransactions();
        return ResponseEntity.ok().build();
    }
}