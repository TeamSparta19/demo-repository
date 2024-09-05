package com.sparta.newsfeed19.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    @NotBlank(message = "수정할 댓글 내용은 필수입니다.")
    @Size(max = 500, message = "댓글은 최대 500자까지 작성할 수 있습니다.")
    private String contents;  // 수정할 댓글 내용
}