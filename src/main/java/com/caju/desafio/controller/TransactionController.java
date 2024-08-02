package com.caju.desafio.controller;

import org.springframework.web.bind.annotation.*;
import com.caju.desafio.controller.model.TransactionRequest;
import com.caju.desafio.controller.model.TransactionResponse;
import com.caju.desafio.repository.model.Transaction;
import com.caju.desafio.service.TransactionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/authorize")
  public Mono<TransactionResponse> authorizeTransaction(@RequestBody TransactionRequest request) {
    return transactionService.save(request)
        .flatMap(transactionService::authorize);
  }

  @GetMapping()
  public Flux<Transaction> getAllTransactions() {
    return transactionService.getAllTransactions();
  }
}

