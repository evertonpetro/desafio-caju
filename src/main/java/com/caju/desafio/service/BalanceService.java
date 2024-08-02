package com.caju.desafio.service;

import com.caju.desafio.repository.BalanceRepository;
import com.caju.desafio.repository.model.Balance;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {
  private final BalanceRepository balanceRepository;

  public Flux<Balance> getAllBalances() {
    return balanceRepository.findAll();
  }
}
