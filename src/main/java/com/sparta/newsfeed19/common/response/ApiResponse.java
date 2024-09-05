package com.sparta.newsfeed19.common.response;

import com.sparta.newsfeed19.common.exception.ApiException;
import com.sparta.newsfeed19.common.exception.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private int code;
    private String message;
    private Object data;

    public static <T> ApiResponse setResponse(ResponseCode responseCode, T data) {
        return new ApiResponse(responseCode.getCode().value(), responseCode.getMessage(), data);
    }

    public static <T> ApiResponse setResponse(ApiException apiException, T data) {
        return new ApiResponse(apiException.getCode().value(), apiException.getMessage(), data);
    }
}