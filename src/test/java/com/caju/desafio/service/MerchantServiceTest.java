package com.caju.desafio.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.caju.desafio.repository.MerchantRepository;
import com.caju.desafio.repository.model.Merchant;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class MerchantServiceTest {

  @Mock
  private MerchantRepository merchantRepository;

  @InjectMocks
  private MerchantService merchantService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private final String MCC = "1234";
  private final String MERCHANT_NAME = "merchant_name";
  private final String CATEGORY = "FOOD";

  @Test
  void testFindByMerchantName_Found() {

    when(merchantRepository.findByMerchantName(anyString()))
        .thenReturn(Mono.just(MCC));

    StepVerifier.create(merchantService.findByMerchantName(MERCHANT_NAME))
        .expectNext(MCC)
        .verifyComplete();
  }

  @Test
  void testFindByMerchantName_NotFound() {

    when(merchantRepository.findByMerchantName(anyString()))
        .thenReturn(Mono.empty());

    StepVerifier.create(merchantService.findByMerchantName(MERCHANT_NAME))
        .verifyComplete();
  }

  @Test
  void testSaveMerchant() {
    var merchant = Merchant.builder()
        .merchantName(MERCHANT_NAME)
        .mcc(MCC)
        .build();


    when(merchantRepository.save(any(Merchant.class)))
        .thenReturn(Mono.just(merchant));

    StepVerifier.create(merchantService.save(MERCHANT_NAME, MCC))
        .expectNext(merchant)
        .verifyComplete();
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

    when(merchantRepository.findAll())
        .thenReturn(Flux.just(merchant1, merchant2));

    StepVerifier.create(merchantService.getAllMerchants())
        .expectNext(merchant1)
        .expectNext(merchant2)
        .verifyComplete();
  }
}
