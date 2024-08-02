package com.caju.desafio.controller.handler;

import com.caju.desafio.controller.model.TransactionResponse;
import com.caju.desafio.exceptions.GenericException;
import com.caju.desafio.exceptions.RejectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler({RejectedException.class})
  ResponseEntity<TransactionResponse> handleRejectedException(RejectedException e) {
    return ResponseEntity.status(HttpStatus.OK).body(TransactionResponse.builder().code("51").build());
  }

  @ExceptionHandler({GenericException.class})
  ResponseEntity<TransactionResponse> handleGenericException(RejectedException e) {
    return ResponseEntity.status(HttpStatus.OK).body(TransactionResponse.builder().code("07").build());
  }

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<TransactionResponse> handleException(WebExchangeBindException e) {
    return ResponseEntity.ok().body(TransactionResponse.builder().code("07").build());
  }
}
