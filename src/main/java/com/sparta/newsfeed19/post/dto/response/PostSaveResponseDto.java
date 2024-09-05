package com.sparta.newsfeed19.post.dto.response;

import com.sparta.newsfeed19.entity.User;
import com.sparta.newsfeed19.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostSaveResponseDto {

    private final Long id;
    private final String email;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private PostSaveResponseDto(
            Long id,
            User user,
            String title,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.email = user.getEmail();
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostSaveResponseDto from(Post post) {
        return new PostSaveResponseDto(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
