package com.sparta.newsfeed19.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId; // 댓글의 고유 ID
    private String userEmail; // 작성자 이메일 (변경된 필드명)
    private String contents; // 댓글 내용
    private LocalDateTime createdAt; // 댓글 생성 시간
    private LocalDateTime updatedAt; // 댓글 수정 시간

    public CommentResponseDto(Long commentId, String userEmail, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.userEmail = userEmail;  // 필드명 변경
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}