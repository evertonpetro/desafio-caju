package com.caju.desafio.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.caju.desafio.repository.model.Balance;
import reactor.core.publisher.Mono;

public interface BalanceRepository extends ReactiveCrudRepository<Balance, Long> {
  Mono<Balance> findByCategory(String category);
}