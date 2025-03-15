package tech.devluan.itau_backend.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import tech.devluan.itau_backend.exception.TransactionValidationException;
import tech.devluan.itau_backend.model.Transaction;

@Service
public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();

    public Transaction createTransaction(Transaction transaction) {
        transaction.setValor(transaction.getValor());
        transaction.setDataHora(transaction.getDataHora());
        transactions.add(transaction);
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
        printTransactions();
    }

    public void validateTransaction(Transaction transaction) {
        List<String> errors = new ArrayList<>();
    
        if (transaction.getValor() == null || transaction.getValor() <= 0) {
            errors.add("O valor da transação deve ser maior que zero");
        }
    
        if (transaction.getDataHora() == null) {
            errors.add("A data da transação é obrigatória");
        } else if (transaction.getDataHora().isAfter(OffsetDateTime.now())) {
            errors.add("A data da transação não pode ser futura");
        }
    
        if (!errors.isEmpty()) {
            throw new TransactionValidationException("Erro na validação da transação", errors);
        }
    }

}
    
