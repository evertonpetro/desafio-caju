package com.caju.desafio.repository.impl;

import com.caju.desafio.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

  private final DatabaseClient databaseClient;

  @Override
  public Mono<String> findCategoryByMcc(String code) {
    var query = String.format("""
        SELECT COALESCE(m.category, 'CASH') AS category
          FROM (SELECT %s AS mcc) AS input
          LEFT JOIN mcc_category m
            ON input.mcc BETWEEN m.start_mcc AND m.end_mcc;
        """, code);
    return databaseClient.sql(query)
        .fetch()
        .first()
        .map(result -> result.get("category").toString());
  }
}
