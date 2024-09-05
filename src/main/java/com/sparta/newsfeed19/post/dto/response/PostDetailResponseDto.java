package com.sparta.newsfeed19.post.dto.response;

import com.sparta.newsfeed19.entity.User;
import com.sparta.newsfeed19.entity.Post;
import lombok.Getter;
import org.springframework.data.domain.Page;

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

    private PostDetailResponseDto(
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

    public static Page<PostDetailResponseDto> from(Page<Post> posts) {
        return posts.map(post -> new PostDetailResponseDto(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        ));
    }
}
