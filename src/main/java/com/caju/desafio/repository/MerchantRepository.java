package com.caju.desafio.repository;

import com.caju.desafio.repository.model.Merchant;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;

@Component
public interface MerchantRepository {
  Mono<Merchant> save(Merchant merchant);
  Mono<String> findByMerchantName(String merchantName);
  Flux<Merchant> findAll();
}
