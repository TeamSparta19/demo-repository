package com.sparta.newsfeed19.follow;

import com.sparta.newsfeed19.follow.dto.FollowRequestDto;
import com.sparta.newsfeed19.follow.dto.FollowerResponseDto;
import com.sparta.newsfeed19.follow.dto.FollowingResponseDto;
import com.sparta.newsfeed19.global.annotation.LoginUser;
import com.sparta.newsfeed19.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.newsfeed19.global.exception.ResponseCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/v1/following")
    public ResponseEntity<?> followV1(@RequestBody FollowRequestDto followRequestDto) {
        followService.follow(followRequestDto.getEmail(), followRequestDto);
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, null), HttpStatus.OK);
    }

    @DeleteMapping("/v1/following")
    public ResponseEntity<?> unfollowV1(@RequestBody FollowRequestDto followRequestDto) {
        followService.unfollow(followRequestDto.getEmail(), followRequestDto);
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, null), HttpStatus.OK);
    }

    @GetMapping("/v1/followers")
    public ResponseEntity<?> getFollowerListV1(@RequestBody FollowRequestDto followRequestDto) {
        List<FollowerResponseDto> followerList = followService.getFollowerList(followRequestDto.getEmail());
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, followerList), HttpStatus.OK);
    }

    @GetMapping("/v1/following")
    public ResponseEntity<?> getFollowingListV1(@RequestBody FollowRequestDto followRequestDto) {
        List<FollowingResponseDto> followingList = followService.getFollowingList(followRequestDto.getEmail());
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, followingList), HttpStatus.OK);
    }

    @PostMapping("/following")
    public ResponseEntity<?> follow(@LoginUser String email, @Valid @RequestBody FollowRequestDto followRequestDto) {
        followService.follow(email, followRequestDto);
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, null), HttpStatus.OK);
    }

    @DeleteMapping("/following")
    public ResponseEntity<?> unfollow(@LoginUser String email, @Valid @RequestBody FollowRequestDto followRequestDto) {
        followService.unfollow(email, followRequestDto);
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, null), HttpStatus.OK);
    }

    @GetMapping("/followers")
    public ResponseEntity<?> getFollowerList(@LoginUser String email) {
        List<FollowerResponseDto> followerList = followService.getFollowerList(email);
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, followerList), HttpStatus.OK);
    }

    @GetMapping("/following")
    public ResponseEntity<?> getFollowingList(@LoginUser String email) {
        List<FollowingResponseDto> followingList = followService.getFollowingList(email);
        return new ResponseEntity<>(ApiResponse.setResponse(SUCCESS, followingList), HttpStatus.OK);
    }

}
