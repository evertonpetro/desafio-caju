package com.caju.desafio.repository;

import org.springframework.stereotype.Component;
import com.caju.desafio.repository.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface TransactionRepository  {
  Mono<Transaction> save(Transaction transaction);
  Flux<Transaction> findAll();
}