package com.sparta.newsfeed19.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId;   // 댓글 고유 ID
    private String email;     // 작성자 이메일
    private String contents;  // 댓글 내용
    private LocalDateTime createdAt;  // 생성일
    private LocalDateTime updatedAt;  // 수정일

    public CommentResponseDto(Long commentId, String email, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.email = email;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}