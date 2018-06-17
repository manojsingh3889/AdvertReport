package org.crealytics.controller;

import org.crealytics.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);
    @ExceptionHandler({ AppException.class })
    public ResponseEntity<AppException.ErrorDetail> handleAccessDeniedException(Exception ex, WebRequest request) {
        AppException e = (AppException) ex;
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        LOGGER.error("Exception occurred");
        LOGGER.error(e.getDetail().getErrormessage());
        LOGGER.debug(e.getMessage(),e);
        return new ResponseEntity<AppException.ErrorDetail>(e.getDetail(), header, HttpStatus.BAD_REQUEST);
    }
}
