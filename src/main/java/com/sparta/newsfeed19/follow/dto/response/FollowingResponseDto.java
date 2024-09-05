package com.sparta.newsfeed19.follow.dto.response;

import com.sparta.newsfeed19.entity.Follow;
import lombok.Getter;

@Getter
public class FollowingResponseDto {
    private long followingId;

    public FollowingResponseDto(long followingId) {
        this.followingId = followingId;
    }

    public static FollowingResponseDto from(Follow follow) {
        return new FollowingResponseDto(follow.getFollowing().getId());
    }
}
