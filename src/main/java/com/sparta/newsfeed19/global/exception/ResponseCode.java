package com.sparta.newsfeed19.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
    // 공통 응답 코드
    SUCCESS("정상 처리되었습니다", HttpStatus.OK),

    // 사용자 응답 코드
    EXIST_EMAIL("존재하는 이메일 입니다.", HttpStatus.BAD_REQUEST)
    // 게시물 응답 코드

    // 댓글 응답 코드

    // 팔로우 응답 코드
    ;

    private final String message;
    private final HttpStatus code;

    ResponseCode(String message, HttpStatus code) {
        this.message = message;
        this.code = code;
    }

}
