package com.mos.global.common.exception;

import com.mos.global.common.ErrorCode;
import com.mos.global.common.message.Result;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Result<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors()
        .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
    return new Result<>(errors).setResultCode("fail").setErrorMessage(ErrorCode.VALIDATION_ERROR);
//    return ResponseEntity.badRequest().body(errors);
  }

}
