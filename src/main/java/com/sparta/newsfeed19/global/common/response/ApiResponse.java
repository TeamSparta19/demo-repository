package com.sparta.newsfeed19.global.common.response;

import com.sparta.newsfeed19.global.exception.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
    private int code;
    private String message;
    private Object data;

    public static <T> ApiResponse setResponse(ResponseCode responseCode, T data) {
        return new ApiResponse(responseCode.getCode().value(), responseCode.getMessage(), data);
    }
}