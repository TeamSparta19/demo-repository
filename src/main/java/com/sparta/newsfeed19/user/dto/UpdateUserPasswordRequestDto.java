package com.sparta.newsfeed19.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserPasswordRequestDto {
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*])[a-zA-Z\\d~!@#$%^&*]{8,}$",
            message = "대소문자 포함 영문 + 숫자 + 특수문자 포함 8자 이상으로 입력 해주세요 ")
    private String newPassword;
}
