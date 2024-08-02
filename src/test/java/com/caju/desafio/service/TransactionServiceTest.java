package com.caju.desafio.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import com.caju.desafio.controller.model.TransactionRequest;
import com.caju.desafio.controller.model.TransactionResponse;
import com.caju.desafio.exceptions.GenericException;
import com.caju.desafio.exceptions.RejectedException;
import com.caju.desafio.repository.BalanceRepository;
import com.caju.desafio.repository.TransactionRepository;
import com.caju.desafio.repository.model.Balance;
import com.caju.desafio.repository.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class TransactionServiceTest {

  @Mock
  private CategoryService categoryService;

  @Mock
  private BalanceRepository balanceRepository;

  @Mock
  private TransactionRepository transactionRepository;

  @Spy
  private ModelMapper modelMapper;

  @InjectMocks
  private TransactionService transactionService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAuthorize_Success() {
    var transaction = Transaction
        .builder()
        .merchant("test_merchant")
        .mcc("1234")
        .totalAmount(100.0)
        .build();

    var balance = Balance
        .builder()
        .amount(200.0)
        .category("FOOD")
        .build();

    when(categoryService.getCategory(anyString(), anyString()))
        .thenReturn(Mono.just("FOOD"));
    when(balanceRepository.findByCategory(anyString()))
        .thenReturn(Mono.just(balance));
    when(balanceRepository.save(any(Balance.class)))
        .thenReturn(Mono.just(balance));

    StepVerifier.create(transactionService.authorize(transaction))
        .expectNext(TransactionResponse.builder().code("00").build())
        .verifyComplete();
  }

  @Test
  void testAuthorize_InsufficientFunds() {
    var transaction = Transaction
        .builder()
        .merchant("test_merchant")
        .mcc("1234")
        .totalAmount(250.0)
        .build();

    var balance = Balance
        .builder()
        .amount(200.0)
        .category("FOOD")
        .build();

    when(categoryService.getCategory(anyString(), anyString()))
        .thenReturn(Mono.just("FOOD"));

    when(balanceRepository.findByCategory(anyString()))
        .thenReturn(Mono.just(balance));

    StepVerifier.create(transactionService.authorize(transaction))
        .expectError(RejectedException.class)
        .verify();
  }

  @Test
  void testSave() {
    var transaction = new Transaction();

    when(transactionRepository.save(any(Transaction.class)))
        .thenReturn(Mono.just(transaction));

    StepVerifier.create(transactionService.save(new TransactionRequest()))
        .expectNext(transaction)
        .verifyComplete();
  }

  @Test
  void testGetAllTransactions() {

    when(transactionRepository.findAll())
        .thenReturn(Flux.just(Transaction.builder().build(), Transaction.builder().build()));

    StepVerifier.create(transactionService.getAllTransactions())
        .expectNextCount(2)
        .verifyComplete();
  }
}