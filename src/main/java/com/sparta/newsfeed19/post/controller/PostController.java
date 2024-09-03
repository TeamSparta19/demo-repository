package com.sparta.newsfeed19.post.controller;

import com.sparta.newsfeed19.global.util.JwtUtil;
import com.sparta.newsfeed19.post.service.PostService;
import com.sparta.newsfeed19.post.dto.request.*;
import com.sparta.newsfeed19.post.dto.response.*;
import com.sparta.newsfeed19.user.User;
import com.sparta.newsfeed19.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    // 게시물 등록
    @PostMapping
    public ResponseEntity<PostSaveResponseDto> savePost(@RequestBody PostSaveRequestDto requestDto) {
        return ResponseEntity.ok(postService.savePost(requestDto));
    }

    // 게시물 조회 (다건, 페이지네이션)
    @GetMapping("/feed")
    public ResponseEntity<Page<PostDetailResponseDto>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        // 현재 사용자 이메일을 기반으로 뉴스피드 조회
        User currentUser = userService.getCurrentUser(request);
        String email = currentUser.getEmail();
        Page<PostDetailResponseDto> posts = postService.getPosts(page, size, email);
        return ResponseEntity.ok(posts);
    }



    // 게시물 조회 단건
    @GetMapping("/{postId}")
    public ResponseEntity<PostSimpleResponseDto> getPost(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    // 게시물 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostUpdateResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return ResponseEntity.ok(postService.updatePost(postId, postUpdateRequestDto));
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        postService.deletePost(postId, request);
        return ResponseEntity.ok().build();
    }
}
