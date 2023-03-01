package com.gsg.assignment.netprice.api;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.gsg.assignment.netprice.exception.CountryNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CountryNotFoundException.class)
  public final ResponseEntity<Object> illegalArgumentException(CountryNotFoundException ex,
      WebRequest request) {
    ProblemDetail body = ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
    return handleExceptionInternal(ex, body, HttpHeaders.EMPTY, BAD_REQUEST, request);
  }
}
