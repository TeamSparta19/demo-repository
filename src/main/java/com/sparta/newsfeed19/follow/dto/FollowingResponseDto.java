package com.sparta.newsfeed19.follow.dto;

import com.sparta.newsfeed19.follow.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowingResponseDto {
    private long followingId;

    public FollowingResponseDto(Follow follow) {
        this.followingId = follow.getFollowing().getId();
    }
}
