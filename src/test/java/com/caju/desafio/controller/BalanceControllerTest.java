package com.caju.desafio.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.caju.desafio.repository.model.Balance;
import com.caju.desafio.service.BalanceService;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BalanceControllerTest {

  @Mock
  private BalanceService balanceService;

  @InjectMocks
  private BalanceController balanceController;

  @BeforeEach
  public void setUp() {
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

    when(balanceService.getAllBalances())
        .thenReturn(Flux.just(balance1, balance2));

    StepVerifier.create(balanceController.getAllBalances())
        .expectNextCount(2)
        .verifyComplete();
  }
}