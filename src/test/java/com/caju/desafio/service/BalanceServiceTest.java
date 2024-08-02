package com.caju.desafio.service;

import static org.mockito.Mockito.when;

import com.caju.desafio.repository.BalanceRepository;
import com.caju.desafio.repository.model.Balance;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BalanceServiceTest {

  @Mock
  private BalanceRepository balanceRepository;

  @InjectMocks
  private BalanceService balanceService;

  public BalanceServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllBalances() {
    var balance1 = Balance.builder()
        .category("FOOD")
        .amount(100.0)
        .build();

    var balance2 = Balance.builder()
        .category("MEAL")
        .amount(100.0)
        .build();

    when(balanceRepository.findAll()).thenReturn(Flux.just(balance1, balance2));

    var result = balanceService.getAllBalances();

    StepVerifier.create(result)
        .expectNext(balance1)
        .expectNext(balance2)
        .verifyComplete();
  }
}