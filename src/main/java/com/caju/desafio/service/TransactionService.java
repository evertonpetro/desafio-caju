package com.caju.desafio.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.caju.desafio.controller.model.TransactionRequest;
import com.caju.desafio.controller.model.TransactionResponse;
import com.caju.desafio.exceptions.GenericException;
import com.caju.desafio.exceptions.RejectedException;
import com.caju.desafio.repository.BalanceRepository;
import com.caju.desafio.repository.TransactionRepository;
import com.caju.desafio.repository.model.Transaction;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {

  private final CategoryService categoryService;
  private final BalanceRepository balanceRepository;
  private final TransactionRepository transactionRepository;
  private final ModelMapper modelMapper;

  @Transactional
  public Mono<TransactionResponse> authorize(Transaction transaction) {
    return categoryService.getCategory(transaction.getMerchant(), transaction.getMcc())
        .flatMap(category -> balanceRepository.findByCategory(category)
            .flatMap(balance -> {
              if (balance.getAmount() >= transaction.getTotalAmount()) {
                balance.setAmount(balance.getAmount() - transaction.getTotalAmount());
                return balanceRepository.save(balance)
                    .thenReturn(TransactionResponse.builder().code("00").build());
              }
              return Mono.error(new RejectedException());
            })
            .switchIfEmpty(Mono.error(new GenericException())));
  }

  public Mono<Transaction> save(TransactionRequest request) {
    var transaction = modelMapper.map(request, Transaction.class);
    return transactionRepository.save(transaction);
  }

  public Flux<Transaction> getAllTransactions() {
    return transactionRepository.findAll();
  }
}

