package tech.devluan.itau_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tech.devluan.itau_backend.model.Statistics;
import tech.devluan.itau_backend.service.TransactionService;

@RestController
@RequestMapping("estatistica")
@RequiredArgsConstructor
public class StatisticsController {

    private final TransactionService transactionService;

    @GetMapping
    public Statistics getStatistics() {
        return transactionService.getStatistics();
    }
}
