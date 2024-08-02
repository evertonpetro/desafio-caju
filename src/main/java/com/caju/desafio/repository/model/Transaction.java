package com.caju.desafio.repository.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import reactor.core.publisher.Mono;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("transactions")
public class Transaction {
  @Id
  private Long id;
  private String account;
  private double totalAmount;
  private String mcc;
  private String merchant;

  public static Mono<Transaction> mapperTo(Map<String, Object> row) {
    return Mono.just(Transaction.builder()
        .id(Long.parseLong(row.get("id").toString()))
        .account(row.get("account").toString())
        .totalAmount(Double.parseDouble(row.get("totalAmount").toString()))
        .mcc(row.get("mcc").toString())
        .merchant(row.get("merchant").toString())
        .build());
  }
}
