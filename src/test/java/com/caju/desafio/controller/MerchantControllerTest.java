package com.caju.desafio.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.caju.desafio.repository.model.Merchant;
import com.caju.desafio.service.MerchantService;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class MerchantControllerTest {

  @Mock
  private MerchantService merchantService;

  @InjectMocks
  private MerchantController merchantController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllMerchants() {
    var merchant1 = Merchant.builder()
        .merchantName("merchant_1")
        .mcc("1111")
        .build();

    var merchant2 = Merchant.builder()
        .merchantName("merchant_2")
        .mcc("2222")
        .build();

    when(merchantService.getAllMerchants())
        .thenReturn(Flux.just(merchant1, merchant2));

    StepVerifier.create(merchantController.getAllMerchants())
        .expectNextCount(2)
        .verifyComplete();
  }
}