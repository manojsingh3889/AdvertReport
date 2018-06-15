package org.crealytics.controller;

import org.crealytics.exception.AppException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * {@link RestExceptionHandler} class is rest exception handler and response back in comprehensive response.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AppException.class })
    public ResponseEntity<AppException.ErrorDetail> handleAccessDeniedException(Exception ex, WebRequest request) {
        AppException e = (AppException) ex;
        return new ResponseEntity<AppException.ErrorDetail>(e.getDetail(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
