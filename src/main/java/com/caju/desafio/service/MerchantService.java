package com.caju.desafio.service;

import com.caju.desafio.repository.MerchantRepository;
import com.caju.desafio.repository.model.Merchant;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantService {

  private final MerchantRepository merchantRepository;

  public Mono<String> findByMerchantName(String merchantName) {
    return merchantRepository.findByMerchantName(merchantName);
  }

  public Mono<Merchant> save(String merchantName, String mcc) {
    return merchantRepository.save(Merchant.builder()
        .merchantName(merchantName)
        .mcc(mcc)
        .build());
  }

  public Flux<Merchant> getAllMerchants() {
    return merchantRepository.findAll();
  }
}
