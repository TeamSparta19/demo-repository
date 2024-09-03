package com.sparta.newsfeed19.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SaveUserResponseDto {
    private final String email;
    private final LocalDateTime createdAt;

    public SaveUserResponseDto(String email, LocalDateTime createdAt) {
        this.email = email;
        this.createdAt = createdAt;
    }
}
