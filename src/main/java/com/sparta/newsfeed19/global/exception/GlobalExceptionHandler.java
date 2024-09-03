package com.sparta.newsfeed19.global.exception;

import com.sparta.newsfeed19.global.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException apiException) {
        ApiResponse apiResponse = new ApiResponse(apiException.getCode().value(), apiException.getMessage(), null);
        return new ResponseEntity<>(apiResponse, apiException.getCode());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponse apiResponse = new ApiResponse(code.value(), exception.getMessage(), null);
        return new ResponseEntity<>(apiResponse, code);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        HttpStatus code = HttpStatus.BAD_REQUEST;

        List<String> fieldErrorList;
        fieldErrorList = exception.getFieldErrors().stream().map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage()).toList();
        ApiResponse apiResponse = ApiResponse.setResponse(ResponseCode.INVALID_REQUEST, fieldErrorList);
        return new ResponseEntity<>(apiResponse, code);
    }
}
