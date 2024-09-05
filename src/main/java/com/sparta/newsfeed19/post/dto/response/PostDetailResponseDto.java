package com.sparta.newsfeed19.post.dto.response;

import com.sparta.newsfeed19.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

// 게시물 다건 조회 responseDto
@Getter
public class PostDetailResponseDto {

    private final Long id;
    private final Long userId;
    private final String email;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostDetailResponseDto(
            Long id,
            User user,
            String title,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userId = user.getId();
        this.email = user.getEmail();
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
