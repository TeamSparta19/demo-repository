package com.sparta.newsfeed19.data;

import com.sparta.newsfeed19.follow.dto.FollowRequestDto;

public class FollowMockData {
    public static FollowRequestDto followRequestDto(long followingId) {
        return new FollowRequestDto(followingId);
    }
}
