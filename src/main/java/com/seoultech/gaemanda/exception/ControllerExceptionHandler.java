package com.seoultech.gaemanda.exception;

import com.seoultech.gaemanda.exception.dto.BadRequestFailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(value = {
      ExistNicknameException.class,
      NotExistMemberException.class
  })
  public ResponseEntity<BadRequestFailResponse> badRequest(Exception e) {
    return ResponseEntity.badRequest()
        .body(BadRequestFailResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(e.getMessage())
            .build()
        );
  }


  @ExceptionHandler(value = {
      BindException.class,
      MethodArgumentNotValidException.class
  })
  public ResponseEntity validationError(BindException e) {
    BindingResult bindingResult = e.getBindingResult();

    StringBuilder builder = new StringBuilder();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      builder.append("[");
      builder.append(fieldError.getField());
      builder.append("](은)는 ");
      builder.append(fieldError.getDefaultMessage());
      builder.append(" 입력된 값: [");
      builder.append(fieldError.getRejectedValue());
      builder.append("]\n");
    }
    return ResponseEntity.badRequest().body(builder.toString());
  }

  private String getMessage(String message) {
    if (StringUtils.hasText(message)) {
      return message + "\n \t {}";
    }
    return "\n \t {}";

  }

}
