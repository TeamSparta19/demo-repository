package com.sparta.newsfeed19.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class PostDeleteResponseDto {

    private final Long postId;
    private final String deleteAt;

    private PostDeleteResponseDto(Long postId) {
        this.postId = postId;
        this.deleteAt = formatCurrentTime(); // 포맷팅된 현재 시간을 저장
    }

    public static PostDeleteResponseDto of(Long postId) {
        return new PostDeleteResponseDto(postId);
    }

    private String formatCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }
}
