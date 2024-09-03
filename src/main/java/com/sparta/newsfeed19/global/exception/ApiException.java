package com.sparta.newsfeed19.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final String message;
    private final HttpStatus code;

    public ApiException(ResponseCode responseCode) {
        super("[" + responseCode.getCode() + "] " + responseCode.getMessage());
        this.message = responseCode.getMessage();
        this.code = responseCode.getCode();
    }
}
