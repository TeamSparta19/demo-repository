package com.sparta.newsfeed19.user.dto.response;

import com.sparta.newsfeed19.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SaveUserResponseDto {
    private final String email;
    private final LocalDateTime createdAt;

    private SaveUserResponseDto(String email, LocalDateTime createdAt) {
        this.email = email;
        this.createdAt = createdAt;
    }

    public static SaveUserResponseDto from(User user) {
        return new SaveUserResponseDto(user.getEmail(), user.getCreatedAt());
    }
}
