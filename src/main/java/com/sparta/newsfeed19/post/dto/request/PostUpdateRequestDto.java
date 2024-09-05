package com.sparta.newsfeed19.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostUpdateRequestDto {
    @NotBlank(message = "수정할 제목은 필수입니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 작성할 수 있습니다.")
    private String title;

    @NotBlank(message = "수정할 내용은 필수입니다.")
    @Size(max = 1000, message = "내용은 최대 1000자까지 작성할 수 있습니다.")
    private String contents;
}
