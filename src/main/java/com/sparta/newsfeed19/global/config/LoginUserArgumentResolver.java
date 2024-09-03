package com.sparta.newsfeed19.global.config;

import com.sparta.newsfeed19.global.annotation.LoginUser;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.sparta.newsfeed19.global.constant.Const.USER_EMAIL;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginUser.class) != null
                && parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return String.valueOf(request.getAttribute(USER_EMAIL));
    }
}
