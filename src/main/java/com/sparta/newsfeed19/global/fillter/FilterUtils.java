package com.sparta.newsfeed19.global.fillter;

import com.sparta.newsfeed19.global.common.response.ApiResponse;
import com.sparta.newsfeed19.global.exception.ApiException;
import com.sparta.newsfeed19.global.util.Utils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

public interface FilterUtils {
    default boolean checkUrlPathIsMatch(String url, String pattern) {
        return Pattern.matches(pattern, url);
    }

    default boolean checkUrlPathIsEquals(String url, List<String> urlList) {
        return urlList.stream().anyMatch(url::equals);
    }

    default boolean checkHttpMethod(String method, List<String> allowedMethods) {
        return allowedMethods.stream().anyMatch(allowedMethod -> allowedMethod.equalsIgnoreCase(method));
    }

    default void sendErrorResponse(HttpServletResponse response, ApiException e) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(e.getCode().value());

        ApiResponse apiResponse = ApiResponse.setResponse(e, null);
        String jsonToString = Utils.objectToJsonString(apiResponse);
        response.getWriter().print(jsonToString);
    }
}