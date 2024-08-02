package com.caju.desafio.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.caju.desafio.controller.model.TransactionRequest;
import com.caju.desafio.controller.model.TransactionResponse;
import com.caju.desafio.exceptions.GenericException;
import com.caju.desafio.repository.model.Transaction;
import com.caju.desafio.service.TransactionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class TransactionControllerTest {

  @Mock
  private TransactionService transactionService;

  @InjectMocks
  private TransactionController transactionController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAuthorizeTransaction_Success() {
    var response = TransactionResponse.builder().code("00").build();

    when(transactionService.save(any(TransactionRequest.class)))
        .thenReturn(Mono.just(new Transaction()));
    when(transactionService.authorize(any(Transaction.class)))
        .thenReturn(Mono.just(response));

    StepVerifier.create(transactionController.authorizeTransaction(new TransactionRequest()))
        .expectNext(response)
        .verifyComplete();
  }

  @Test
  void testAuthorizeTransaction_Failure() {

    when(transactionService.save(any(TransactionRequest.class)))
        .thenReturn(Mono.just(new Transaction()));
    when(transactionService.authorize(any(Transaction.class)))
        .thenReturn(Mono.error(new GenericException()));

    StepVerifier.create(transactionController.authorizeTransaction(new TransactionRequest()))
        .expectError(GenericException.class)
        .verify();
  }

  @Test
  void testGetAllTransactions() {

    when(transactionService.getAllTransactions())
        .thenReturn(Flux.just(new Transaction(), new Transaction()));

    StepVerifier.create(transactionController.getAllTransactions())
        .expectNextCount(2)
        .verifyComplete();
  }
}