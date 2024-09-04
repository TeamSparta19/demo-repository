package com.sparta.newsfeed19.follow.dto;

import com.sparta.newsfeed19.follow.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowerResponseDto {
    private long followerId;

    public FollowerResponseDto(Follow follow) {
        this.followerId = follow.getFollower().getId();
    }
}
