package com.caju.desafio.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
  private String account;
  private Double totalAmount;
  private String mcc;
  private String merchant;
}
