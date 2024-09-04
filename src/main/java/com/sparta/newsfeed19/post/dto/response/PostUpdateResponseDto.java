package com.sparta.newsfeed19.post.dto.response;

import com.sparta.newsfeed19.user.User;
import com.sparta.newsfeed19.user.dto.UserDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostUpdateResponseDto {

    private final Long id;
    private final UserDto user;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostUpdateResponseDto(
            Long id,
            User user,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.user = new UserDto(
                user.getId(),
                user.getEmail()
        );
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
