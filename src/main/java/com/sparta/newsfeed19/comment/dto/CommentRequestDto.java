package com.sparta.newsfeed19.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    private Long postId;  // 댓글이 속한 게시물의 ID
    private String contents;  // 댓글 내용
}