package com.sparta.newsfeed19.user.dto.response;

import com.sparta.newsfeed19.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserResponseDto {
    private String email;
    private LocalDateTime createdAt;

    private GetUserResponseDto(String email, LocalDateTime createdAt) {
        this.email = email;
        this.createdAt = createdAt;
    }

    public static GetUserResponseDto of(User user) {
        return new GetUserResponseDto(user.getEmail(), user.getCreatedAt());
    }
}
