package com.sparta.newsfeed19.follow.dto.response;

import com.sparta.newsfeed19.entity.Follow;
import lombok.Getter;

@Getter
public class FollowerResponseDto {
    private long followerId;

    public FollowerResponseDto(long followerId) {
        this.followerId = followerId;
    }

    public static FollowerResponseDto from(Follow follow) {
        return new FollowerResponseDto(follow.getFollower().getId());
    }
}
