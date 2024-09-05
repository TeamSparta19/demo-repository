package com.sparta.newsfeed19.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserPasswordRequestDto {
    @NotBlank(message = "현재 비밀번호는 필수입니다.")
    private String oldPassword;

    @NotBlank(message = "새로운 비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*])[a-zA-Z\\d~!@#$%^&*]{8,20}$",
            message = "대소문자 포함 영문 + 숫자 + 특수문자 포함 8자 이상으로 입력 해주세요 ")
    private String newPassword;
}
