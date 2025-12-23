package com.ra.advice;

import com.ra.exception.HttpConflict;
import com.ra.model.dto.DataResponse;
import com.ra.model.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandleException {
    /**
     * @param e MethodArgumentNotValidException
     * @apiNote handle valid exception for validate (400)
     * @return JSON data (message,statusCode,data filedErr)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String,String> errors = new HashMap<>();
        e.getFieldErrors().forEach((error)->{
            errors.put(error.getField(),error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseWrapper.builder()
                        .message("BAD REQUEST")
                        .code(HttpStatus.BAD_REQUEST.value())
                        .dataResponse(errors)
                        .build()
        );
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                DataResponse.builder()
                        .message("NOT FOUND")
                        .httpStatusCode(HttpStatus.NOT_FOUND.value())
                        .build()
        );
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                DataResponse.builder()
                        .message("METHOD_NOT_ALLOWED")
                        .httpStatusCode(HttpStatus.METHOD_NOT_ALLOWED.value()).build()
        );
    }
    @ExceptionHandler(HttpConflict.class)
    public ResponseEntity<?> handleHttpConflict(HttpConflict e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                DataResponse.builder()
                        .message(e.getMessage())
                        .httpStatusCode(HttpStatus.CONFLICT.value())
                        .build()
        );
    }
}
