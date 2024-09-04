package com.sparta.newsfeed19.follow;

import com.sparta.newsfeed19.follow.dto.FollowRequestDto;
import com.sparta.newsfeed19.follow.dto.FollowerResponseDto;
import com.sparta.newsfeed19.follow.dto.FollowingResponseDto;
import com.sparta.newsfeed19.global.exception.ApiException;
import com.sparta.newsfeed19.user.User;
import com.sparta.newsfeed19.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.sparta.newsfeed19.global.exception.ResponseCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void follow(String email, FollowRequestDto followRequestDto) {
        User requestUser = userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER));

        if (Objects.equals(followRequestDto.getFollowingId(), requestUser.getId())) {
            throw new ApiException(INVALID_FOLLOW_REQUEST);
        }

        boolean isAlreadyFollow = requestUser.getFollowingList()
                .stream()
                .anyMatch(following -> Objects.equals(following.getFollowing().getId(), followRequestDto.getFollowingId()));

        if (isAlreadyFollow) {
            throw new ApiException(ALREADY_FOLLOWED_USER);
        }

        User followingUser = userRepository.findByIdAndDeletedAtIsNull(followRequestDto.getFollowingId())
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER));

        followRepository.save(new Follow(requestUser, followingUser));
    }

    @Transactional
    public void unfollow(String email, FollowRequestDto followRequestDto) {
        User requestUser = userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER));

        boolean isAlreadyFollow = requestUser.getFollowingList()
                .stream()
                .anyMatch(following -> Objects.equals(following.getFollowing().getId(), followRequestDto.getFollowingId()));

        if (!isAlreadyFollow) {
            throw new ApiException(ALREADY_UNFOLLOWED_USER);
        }

        followRepository.deleteByFollowerIdAndFollowingId(requestUser.getId(), followRequestDto.getFollowingId());
    }

    @Transactional
    public List<FollowerResponseDto> getFollowerList(String email) {
        return followRepository.findByFollowingEmail(email)
                .stream()
                .map(FollowerResponseDto::new)
                .toList();
    }

    @Transactional
    public List<FollowingResponseDto> getFollowingList(String email) {
        return followRepository.findByFollowerEmail(email)
                .stream()
                .map(FollowingResponseDto::new)
                .toList();
    }

}
