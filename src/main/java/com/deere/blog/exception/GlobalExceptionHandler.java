package com.deere.blog.exception;

import com.deere.blog.payload.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.sql.SQLDataException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final long serialVersionUID = 1L;
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false , "404"),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        Map<String,String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)-> {
            resp.put(((FieldError) error).getField(),error.getDefaultMessage());
        });
        return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false , "500"),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false , "406"),HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<Object> handleClassNotFoundException(ClassNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false , "404"),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLDataException.class)
    public ResponseEntity<Object> handleSQLDataException(SQLDataException exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false , "500"),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExptn(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false , "500"),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException(IOException exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false , "500"),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> expiredJwtException(ExpiredJwtException exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false , "500"),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(exception.getMessage(), false , "400"),HttpStatus.BAD_REQUEST);
    }

}
