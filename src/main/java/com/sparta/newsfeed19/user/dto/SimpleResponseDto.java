package com.sparta.newsfeed19.user.dto;

import com.sparta.newsfeed19.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SimpleResponseDto {

        private String email;
        private LocalDateTime createdAt;

    public SimpleResponseDto(User user) {
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
    }
}
