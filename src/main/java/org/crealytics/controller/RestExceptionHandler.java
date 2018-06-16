package org.crealytics.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.crealytics.exception.AppException;
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
private static final Logger LOGGER = LogManager.getLogger(RestExceptionHandler.class);
    @ExceptionHandler({ AppException.class })
    public ResponseEntity<AppException.ErrorDetail> handleAccessDeniedException(Exception ex, WebRequest request) {
        AppException e = (AppException) ex;
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        LOGGER.info("Exception occurred");
        LOGGER.info(((AppException) ex).getDetail().getErrormessage());
        return new ResponseEntity<AppException.ErrorDetail>(e.getDetail(), header, HttpStatus.BAD_REQUEST);
    }
}
