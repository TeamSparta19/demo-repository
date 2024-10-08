package com.sparta.newsfeed19.post.dto.response;

import com.sparta.newsfeed19.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

// 게시물 단건 조회 responseDto
@Getter
public class PostSimpleResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private PostSimpleResponseDto(
            Long id,
            String title,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostSimpleResponseDto from(Post post) {
        return new PostSimpleResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
