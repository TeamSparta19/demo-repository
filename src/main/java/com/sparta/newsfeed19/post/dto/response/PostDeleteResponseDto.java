package com.sparta.newsfeed19.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
// 일정 삭제 responseDto
public class PostDeleteResponseDto {

    private final Long postId;
    private final String deleteAt;

    public PostDeleteResponseDto (Long postId){
        this.postId= postId;
        this.deleteAt = formatCurrentTime(); // 포맷팅된 현재 시간을 저장
    }

    private String formatCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }
}
