package com.sparta.newsfeed19.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
    // 공통 응답 코드
    SUCCESS("정상 처리되었습니다", HttpStatus.OK),
    INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),

    // 사용자 응답 코드
    NOT_FOUND_USER("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),

    // 게시물 응답 코드
    NOT_FOUND_POST("존재하지 않는 게시물입니다.", HttpStatus.NOT_FOUND),

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
