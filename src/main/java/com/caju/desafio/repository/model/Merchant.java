package com.caju.desafio.repository.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import reactor.core.publisher.Mono;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("merchants")
public class Merchant {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String merchantName;
  private String mcc;

  public static Mono<Merchant> mapperTo(Map<String, Object> row) {
    return Mono.just(Merchant.builder()
        .id(Long.parseLong(row.get("id").toString()))
        .merchantName(row.get("merchant_name").toString())
        .mcc(row.get("mcc").toString())
        .build());
  }
}
