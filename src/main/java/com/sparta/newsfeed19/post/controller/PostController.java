package com.sparta.newsfeed19.post.controller;

import com.sparta.newsfeed19.global.annotation.LoginUser;
import com.sparta.newsfeed19.global.common.response.ApiResponse;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.post.dto.request.PostSaveRequestDto;
import com.sparta.newsfeed19.post.dto.request.PostUpdateRequestDto;
import com.sparta.newsfeed19.post.dto.response.*;
import com.sparta.newsfeed19.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 등록
    @PostMapping
    public ResponseEntity<ApiResponse> savePost(
            @RequestBody PostSaveRequestDto requestDto,
            @LoginUser String email // 로그인된 사용자 정보 자동 주입
    ) {
        PostSaveResponseDto responseDto = postService.savePost(requestDto, email);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, responseDto));
    }

    // 게시물 조회 단건
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse> getPost(
            @PathVariable("postId") Long postId,
            @LoginUser String email
    ) {
        PostSimpleResponseDto post = postService.getPost(postId, email);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, post));
    }

    // 게시물 조회 다건
    @GetMapping("/feed")
    public ResponseEntity<ApiResponse> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @LoginUser String email // 로그인된 사용자 정보 자동 주입
    ) {
        Page<PostDetailResponseDto> posts = postService.getPosts(page, size, email);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, posts));
    }

    // 게시물 수정
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto postUpdateRequestDto,
            @LoginUser String email
    ) {
        PostUpdateResponseDto response = postService.updatePost(postId, postUpdateRequestDto, email);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, response));
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(
            @PathVariable Long postId,
            @LoginUser String email
    ) {
        PostDeleteResponseDto response = postService.deletePost(postId, email);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, response));
    }
}
