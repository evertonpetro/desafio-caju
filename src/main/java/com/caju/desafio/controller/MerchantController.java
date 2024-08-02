package com.caju.desafio.controller;

import com.caju.desafio.repository.model.Merchant;
import com.caju.desafio.service.MerchantService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/merchants")
public class MerchantController {

  private final MerchantService merchantService;

  @GetMapping()
  public Flux<Merchant> getAllMerchants() {
    return merchantService.getAllMerchants();
  }
}
