package com.caju.desafio.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.caju.desafio.exceptions.RejectedException;
import com.caju.desafio.repository.CategoryRepository;
import com.caju.desafio.repository.model.Merchant;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CategoryServiceTest {

  @Mock
  private MerchantService merchantService;

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;

  private final String MCC = "1234";
  private final String MERCHANT_NAME = "merchant_name";
  private final String CATEGORY = "FOOD";

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetCategoryByMerchantName_WhenMerchantExists() {

    when(merchantService.findByMerchantName(anyString()))
        .thenReturn(Mono.just(MCC));
    when(merchantService.save(anyString(), anyString()))
        .thenReturn(Mono.just(Merchant.builder().merchantName(MERCHANT_NAME).mcc(MCC).build()));
    when(categoryRepository.findCategoryByMcc(anyString()))
        .thenReturn(Mono.just(CATEGORY));

    var result = categoryService.getCategory(MERCHANT_NAME, MCC);

    StepVerifier.create(result)
        .expectNext("FOOD")
        .verifyComplete();
  }

  @Test
  void testGetCategoryByMerchantName_WhenMerchantDoesNotExistAndMccIsValid() {

    when(merchantService.findByMerchantName(anyString()))
        .thenReturn(Mono.empty());

    when(merchantService.save(anyString(), anyString()))
        .thenReturn(Mono.just(Merchant.builder().merchantName(MERCHANT_NAME).mcc(MCC).build()));

    when(categoryRepository.findCategoryByMcc(anyString()))
        .thenReturn(Mono.just(CATEGORY));

    Mono<String> result = categoryService.getCategory(MERCHANT_NAME, MCC);

    StepVerifier.create(result)
        .expectNext("FOOD")
        .verifyComplete();
  }

  @Test
  void testGetCategoryByMerchantName_WhenMerchantDoesNotExistAndMccIsInvalid() {

    when(merchantService.findByMerchantName(anyString()))
        .thenReturn(Mono.empty());

    StepVerifier.create(categoryService.getCategory(MERCHANT_NAME, "123"))
        .expectError(RejectedException.class)
        .verify();
  }

  @Test
  void testGetCategoryByMcc_WhenNoCategoryFound() {

    when(merchantService.findByMerchantName(anyString()))
        .thenReturn(Mono.empty());
    when(merchantService.save(anyString(), anyString()))
        .thenReturn(Mono.just(Merchant.builder().merchantName(MERCHANT_NAME).mcc(MCC).build()));
    when(categoryRepository.findCategoryByMcc(anyString()))
        .thenReturn(Mono.empty());

    StepVerifier.create(categoryService.getCategory(MERCHANT_NAME, MCC))
        .expectNext("CASH")
        .verifyComplete();
  }
}