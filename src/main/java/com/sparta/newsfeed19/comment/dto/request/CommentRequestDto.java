package com.sparta.newsfeed19.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    @NotNull(message = "게시물 고유번호는 필수입니다.")
    private Long postId;  // 댓글이 속한 게시물의 ID

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 500, message = "댓글은 최대 500자까지 작성할 수 있습니다.")
    private String contents;  // 댓글 내용
}