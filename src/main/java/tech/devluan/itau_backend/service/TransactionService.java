package tech.devluan.itau_backend.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tech.devluan.itau_backend.exception.TransactionValidationException;
import tech.devluan.itau_backend.model.Transaction;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private List<Transaction> transactions = new ArrayList<>();

    public Transaction createTransaction(Transaction transaction) {
        logger.info("Criando transação: {}", transaction);
        transaction.setValor(transaction.getValor());
        transaction.setDataHora(transaction.getDataHora());
        transactions.add(transaction);
        logger.info("Transação criada com sucesso: {}", transaction);
        printTransactions();
        return transaction;
    }

    private void printTransactions() {
        System.out.println("Lista de Transações:");
        for (Transaction t : transactions) {
            System.out.println("Valor: " + t.getValor() + ", Data/Hora: " + t.getDataHora());
        }
    }

    public void deleteAllTransactions() {
        transactions.clear();
        logger.info("Todas as transações foram deletadas");
        printTransactions();
    }

    public void validateTransaction(Transaction transaction) {
        List<String> errors = new ArrayList<>();
    
        if (transaction.getValor() == null || transaction.getValor() <= 0) {
            logger.error("O valor da transação deve ser maior que zero");
            errors.add("O valor da transação deve ser maior que zero");
        }
    
        if (transaction.getDataHora() == null) {
            logger.error("A data da transação é obrigatória");
            errors.add("A data da transação é obrigatória");
        } else if (transaction.getDataHora().isAfter(OffsetDateTime.now())) {
            logger.error("A data da transação não pode ser futura");
            errors.add("A data da transação não pode ser futura");
        }
    
        if (!errors.isEmpty()) {
            logger.error("Erro na validação da transação: {}", errors);
            throw new TransactionValidationException("Erro na validação da transação", errors);
        }
    }

}
    
