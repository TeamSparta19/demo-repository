package com.sparta.newsfeed19.post.dto.response;

import com.sparta.newsfeed19.entity.User;
import com.sparta.newsfeed19.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostUpdateResponseDto {

    private final Long id;
    private final Long userId;
    private final String email;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private PostUpdateResponseDto(
            Long id,
            User user,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userId = user.getId();
        this.email = user.getEmail();
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostUpdateResponseDto from(Post post) {
        return new PostUpdateResponseDto(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
