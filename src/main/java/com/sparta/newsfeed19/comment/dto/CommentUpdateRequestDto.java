package com.sparta.newsfeed19.comment.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private String contents;  // 수정할 댓글 내용
}