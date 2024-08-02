package com.caju.desafio.controller;

import com.caju.desafio.repository.model.Balance;
import com.caju.desafio.service.BalanceService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balances")
public class BalanceController {

  private final BalanceService balanceService;

  @GetMapping()
  public Flux<Balance> getAllBalances() {
    return balanceService.getAllBalances();
  }
}
