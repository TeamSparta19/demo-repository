package com.sparta.newsfeed19.follow.service;

import com.sparta.newsfeed19.entity.Follow;
import com.sparta.newsfeed19.follow.dto.request.FollowRequestDto;
import com.sparta.newsfeed19.follow.dto.response.FollowerResponseDto;
import com.sparta.newsfeed19.follow.dto.response.FollowingResponseDto;
import com.sparta.newsfeed19.common.exception.ApiException;
import com.sparta.newsfeed19.entity.User;
import com.sparta.newsfeed19.follow.repository.FollowRepository;
import com.sparta.newsfeed19.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.sparta.newsfeed19.common.exception.ResponseCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public void follow(String email, FollowRequestDto followRequestDto) {
        User requestUser = userRepository.findActiveUserByEmail(email);

        checkFollowTarget(requestUser.getId(), followRequestDto.getFollowingId());

        if (checkAlreadyFollow(followRequestDto, requestUser)) {
            throw new ApiException(ALREADY_FOLLOWED_USER);
        }

        User targetUser = userRepository.findActiveUserById(followRequestDto.getFollowingId());

        followRepository.save(Follow.from(requestUser, targetUser));
    }

    public void unfollow(String email, FollowRequestDto followRequestDto) {
        User requestUser = userRepository.findActiveUserByEmail(email);

        checkFollowTarget(requestUser.getId(), followRequestDto.getFollowingId());

        if (!checkAlreadyFollow(followRequestDto, requestUser)) {
            throw new ApiException(ALREADY_UNFOLLOWED_USER);
        }

        followRepository.deleteByFollowerIdAndFollowingId(requestUser.getId(), followRequestDto.getFollowingId());
    }

    private void checkFollowTarget(long requestUserId, long targetUserId) {
        if (requestUserId == targetUserId) {
            throw new ApiException(INVALID_FOLLOW_REQUEST);
        }
    }

    private boolean checkAlreadyFollow(FollowRequestDto followRequestDto, User requestUser) {
        return requestUser.getFollowingList()
                .stream()
                .anyMatch(following -> Objects.equals(following.getFollowing().getId(), followRequestDto.getFollowingId()));
    }

    @Transactional(readOnly = true)
    public List<FollowerResponseDto> getFollowerList(String email) {
        return followRepository.findByFollowingEmail(email)
                .stream()
                .map(FollowerResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FollowingResponseDto> getFollowingList(String email) {
        return followRepository.findByFollowerEmail(email)
                .stream()
                .map(FollowingResponseDto::from)
                .toList();
    }

}
