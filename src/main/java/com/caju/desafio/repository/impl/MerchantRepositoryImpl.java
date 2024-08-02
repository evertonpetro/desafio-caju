package com.caju.desafio.repository.impl;

import com.caju.desafio.repository.MerchantRepository;
import com.caju.desafio.repository.model.Merchant;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantRepositoryImpl implements MerchantRepository {

  private final DatabaseClient databaseClient;

  @Override
  public Mono<Merchant> save(Merchant merchant) {
    return databaseClient.sql("INSERT INTO merchants(merchant_name, mcc) VALUES (:a, :b)")
        .bind("a", merchant.getMerchantName())
        .bind("b", merchant.getMcc())
        .fetch()
        .first()
        .thenReturn(merchant);
  }

  @Override
  public Mono<String> findByMerchantName(String merchantName) {
    var query = String.format("""
        SELECT mcc
          FROM merchants
         WHERE merchant_name = '%s'
        """, merchantName);
    return databaseClient.sql(query)
        .fetch()
        .first()
        .map(result -> result.get("mcc").toString());
  }

  @Override
  public Flux<Merchant> findAll() {
    return databaseClient.sql("SELECT * FROM merchants")
        .fetch()
        .all()
        .flatMap(Merchant::mapperTo);
  }
}
