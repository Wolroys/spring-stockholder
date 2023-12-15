package com.wolroys.springstockapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({StockNotFoundException.class})
    public ResponseEntity<ErrorDto> handlerNotFound(Exception ex){
        return new ResponseEntity<ErrorDto>(new ErrorDto(ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
}
