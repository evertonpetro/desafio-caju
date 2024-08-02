package com.caju.desafio.repository.impl;

import com.caju.desafio.repository.TransactionRepository;
import com.caju.desafio.repository.model.Transaction;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

  private final DatabaseClient databaseClient;

  @Override
  public Mono<Transaction> save(Transaction transaction) {
    return databaseClient.sql("INSERT INTO transactions(account, totalAmount, mcc, merchant) VALUES (:a, :b, :c, :d)")
        .bind("a", transaction.getAccount())
        .bind("b", transaction.getTotalAmount())
        .bind("c", transaction.getMcc())
        .bind("d", transaction.getMerchant())
        .fetch()
        .first()
        .thenReturn(transaction);
  }

  @Override
  public Flux<Transaction> findAll() {
    return databaseClient.sql("SELECT * FROM transactions")
        .fetch()
        .all()
        .flatMap(Transaction::mapperTo);
  }
}
