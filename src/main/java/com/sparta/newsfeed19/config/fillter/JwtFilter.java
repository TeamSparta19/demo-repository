package com.sparta.newsfeed19.config.fillter;

import com.sparta.newsfeed19.common.exception.ApiException;
import com.sparta.newsfeed19.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.sparta.newsfeed19.common.constant.Const.AUTHORIZATION_HEADER;
import static com.sparta.newsfeed19.common.constant.Const.USER_EMAIL;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter implements FilterUtils {

    private final JwtUtil jwtUtil;
    private final List<String> whiteList = List.of("/api/users", "/api/users/login");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("filter started.");
        String url = request.getRequestURI();

        if (checkUrlPathIsEquals(url, whiteList)
                && checkHttpMethod(request.getMethod(), List.of("POST"))) {
            filterChain.doFilter(request, response);
        } else {
            String bearerJwt = request.getHeader(AUTHORIZATION_HEADER);

            try {
                String jwt = jwtUtil.substringToken(bearerJwt);
                log.info("jwt token is not null.");

                jwtUtil.validateToken(jwt);
                log.info("jwt token is valid.");

                Claims claims = jwtUtil.extractClaims(jwt);
                request.setAttribute(USER_EMAIL, claims.getSubject());
                log.info("email = {}", claims.getSubject());

                filterChain.doFilter(request, response);
            } catch (ApiException e) {
                sendErrorResponse(response, e);
            }
        }
    }
}