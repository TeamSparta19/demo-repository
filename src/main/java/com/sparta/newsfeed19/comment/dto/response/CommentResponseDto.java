package com.sparta.newsfeed19.comment.dto.response;

import com.sparta.newsfeed19.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId; // 댓글의 고유 ID
    private String email; // 작성자 이메일
    private String contents; // 댓글 내용
    private LocalDateTime createdAt; // 댓글 생성 시간
    private LocalDateTime updatedAt; // 댓글 수정 시간

    public CommentResponseDto(Long commentId, String email, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.email = email;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getUser().getEmail(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}