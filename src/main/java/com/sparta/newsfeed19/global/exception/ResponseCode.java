package com.sparta.newsfeed19.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
    // 공통 응답 코드
    SUCCESS( "정상 처리되었습니다.", HttpStatus.OK),

    // 사용자 응답 코드
    USER_NOT_FOUND( "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    EXIST_EMAIL("존재하는 이메일 입니다.", HttpStatus.BAD_REQUEST),
    // 게시물 응답 코드
    POST_NOT_FOUND("게시물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_CREATION_FAILED( "게시물 생성에 실패했습니다.", HttpStatus.BAD_REQUEST),
    POST_UPDATE_FAILED( "게시물 수정에 실패했습니다.", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST("인증 요청에 실패했습니다", HttpStatus.BAD_REQUEST );

    // 댓글 응답 코드

    // 팔로우 응답 코드

    private final String message;
    private final HttpStatus code;

    ResponseCode(String message, HttpStatus code) {
        this.message = message;
        this.code = code;
    }
}
