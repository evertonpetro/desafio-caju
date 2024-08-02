package com.caju.desafio.service;

import com.caju.desafio.exceptions.RejectedException;
import com.caju.desafio.repository.CategoryRepository;
import com.caju.desafio.repository.model.Merchant;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final MerchantService merchantService;
  private final CategoryRepository categoryRepository;

  public Mono<String> getCategory(String merchantName, String mcc) {
    return merchantService.findByMerchantName(merchantName)
        .switchIfEmpty(saveMerchant(merchantName, mcc))
        .flatMap(this::getCategoryByMcc);
  }

  private Mono<String> saveMerchant(String merchantName, String mcc) {
    if (isValidMcc(mcc)) {
      return merchantService.save(merchantName, mcc)
          .map(Merchant::getMcc);
    }
    return Mono.error(new RejectedException());
  }

  private Mono<String> getCategoryByMcc(String mcc) {
    return categoryRepository.findCategoryByMcc(mcc)
        .defaultIfEmpty("CASH");
  }

  private boolean isValidMcc(String mcc) {
    return mcc != null && mcc.matches("\\d{4}");
  }
}
