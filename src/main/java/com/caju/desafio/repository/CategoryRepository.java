package com.caju.desafio.repository;

import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;

@Component
public interface CategoryRepository {
  Mono<String> findCategoryByMcc(String mcc);
}
