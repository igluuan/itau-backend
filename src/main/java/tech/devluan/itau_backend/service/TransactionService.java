package tech.devluan.itau_backend.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tech.devluan.itau_backend.exception.TransactionValidationException;
import tech.devluan.itau_backend.model.Statistics;
import tech.devluan.itau_backend.model.Transaction;

/**
 * Service responsible for managing financial transactions and their statistics.
 * This service provides functionality to create, validate, and delete transactions,
 * as well as calculate statistical metrics for all stored transactions.
 *
 * @author devluan
 * @version 1.0
 * @since 1.0
 */
@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private List<Transaction> transactions = new ArrayList<>();

    /**
     * Creates a new transaction and adds it to the transaction list.
     * The transaction is logged both before and after creation for tracking purposes.
     *
     * @param transaction The transaction object to be created
     * @return The created transaction object
     * @see Transaction
     */
    public Transaction createTransaction(Transaction transaction) {
        logger.info("Criando transação: {}", transaction);
        transaction.setValor(transaction.getValor());
        transaction.setDataHora(transaction.getDataHora());
        transactions.add(transaction);
        logger.info("Transação criada com sucesso: {}", transaction);
        printTransactions();
        return transaction;
    }

    /**
     * Removes all transactions from the system.
     * This operation is irreversible and will clear all stored transactions.
     * The operation is logged for audit purposes.
     */
    public void deleteAllTransactions() {
        transactions.clear();
        logger.info("Todas as transações foram deletadas");
        printTransactions();
    }

    /**
     * Validates a transaction according to business rules.
     * The following rules are checked:
     * <ul>
     *   <li>Transaction value must be greater than zero</li>
     *   <li>Transaction date must not be null</li>
     *   <li>Transaction date must not be in the future</li>
     * </ul>
     *
     * @param transaction The transaction to be validated
     * @throws TransactionValidationException if any validation rule is violated
     */
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

    /**
     * Calculates statistical metrics for all stored transactions.
     * The metrics include:
     * <ul>
     *   <li>Count of transactions</li>
     *   <li>Sum of all transaction values</li>
     *   <li>Average transaction value</li>
     *   <li>Minimum transaction value</li>
     *   <li>Maximum transaction value</li>
     * </ul>
     * If no transactions exist, returns statistics with all values set to zero.
     *
     * @return Statistics object containing the calculated metrics
     * @see Statistics
     */
    public Statistics getStatistics() {
        logger.debug("Calculando estatísticas");

        if (transactions.isEmpty()) {
            logger.info("Nenhuma transação encontrada, retornando estatísticas padrão");
            return createEmptyStatistics();
        }

        // Calculate statistics using Java Stream API
        DoubleSummaryStatistics transactionStats = transactions.stream()
                .map(Transaction::getValor)
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        // Create and populate Statistics object
        Statistics statistics = new Statistics();
        statistics.setCount(transactionStats.getCount());
        statistics.setSum(transactionStats.getSum());
        statistics.setAvg(transactionStats.getAverage());
        statistics.setMin(transactionStats.getMin());
        statistics.setMax(transactionStats.getMax());

        logger.debug("Estatísticas calculadas com sucesso: count={}, sum={}, avg={}, min={}, max={}",
                statistics.getCount(), statistics.getSum(), statistics.getAvg(),
                statistics.getMin(), statistics.getMax());

        return statistics;
    }

    /**
     * Creates a Statistics object with all values initialized to zero.
     * This is used when there are no transactions in the system.
     *
     * @return Statistics object with default values (all zeros)
     * @see Statistics
     */
    private Statistics createEmptyStatistics() {
        Statistics emptyStats = new Statistics();
        emptyStats.setCount(0);
        emptyStats.setSum(0);
        emptyStats.setAvg(0);
        emptyStats.setMin(0);
        emptyStats.setMax(0);
        return emptyStats;
    }

    /**
     * Prints the current list of transactions to the console.
     * This method is used for debugging purposes and prints each transaction's
     * value and timestamp.
     */
    private void printTransactions() {
        System.out.println("Lista de Transações:");
        for (Transaction t : transactions) {
            System.out.println("Valor: " + t.getValor() + ", Data/Hora: " + t.getDataHora());
        }
    }
}
    
