package com.sparta.newsfeed19.follow.controller;

import com.sparta.newsfeed19.follow.service.FollowService;
import com.sparta.newsfeed19.follow.dto.request.FollowRequestDto;
import com.sparta.newsfeed19.follow.dto.response.FollowerResponseDto;
import com.sparta.newsfeed19.follow.dto.response.FollowingResponseDto;
import com.sparta.newsfeed19.common.annotation.LoginUser;
import com.sparta.newsfeed19.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.newsfeed19.common.exception.ResponseCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    // 친구 등록
    @PostMapping("/following")
    public ResponseEntity<?> follow(@LoginUser String email, @Valid @RequestBody FollowRequestDto followRequestDto) {
        followService.follow(email, followRequestDto);
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, null), HttpStatus.OK);
    }

    // 친구 삭제
    @DeleteMapping("/following")
    public ResponseEntity<?> unfollow(@LoginUser String email, @Valid @RequestBody FollowRequestDto followRequestDto) {
        followService.unfollow(email, followRequestDto);
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, null), HttpStatus.OK);
    }

    // 나를 팔로워 한 친구 조회
    @GetMapping("/followers")
    public ResponseEntity<?> getFollowerList(@LoginUser String email) {
        List<FollowerResponseDto> followerList = followService.getFollowerList(email);
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, followerList), HttpStatus.OK);
    }

    // 내가 팔로윙한 친구 조회
    @GetMapping("/following")
    public ResponseEntity<?> getFollowingList(@LoginUser String email) {
        List<FollowingResponseDto> followingList = followService.getFollowingList(email);
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, followingList), HttpStatus.OK);
    }

}
