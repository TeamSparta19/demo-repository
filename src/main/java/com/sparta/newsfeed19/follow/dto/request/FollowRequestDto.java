package com.sparta.newsfeed19.follow.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FollowRequestDto {
    @NotNull(message = "(언)팔로우 할 유저의 고유번호는 필수입니다.")
    private Long followingId;
}