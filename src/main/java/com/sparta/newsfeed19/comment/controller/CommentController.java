package com.sparta.newsfeed19.comment.controller;

import com.sparta.newsfeed19.comment.CommentService;
import com.sparta.newsfeed19.comment.dto.request.CommentRequestDto;
import com.sparta.newsfeed19.comment.dto.request.CommentUpdateRequestDto;
import com.sparta.newsfeed19.comment.dto.response.CommentResponseDto;
import com.sparta.newsfeed19.global.annotation.LoginUser;
import com.sparta.newsfeed19.global.common.response.ApiResponse;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping
    public ResponseEntity<ApiResponse> createComment(
            @Valid @RequestBody CommentRequestDto commentRequestDto,
            @LoginUser String email
    ) {
        CommentResponseDto createdComment = commentService.createComment(commentRequestDto, email);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, createdComment));
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,
            @LoginUser String email
    ) {
        CommentResponseDto updatedComment = commentService.updateComment(id, commentUpdateRequestDto, email);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, updatedComment));
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Long id,
            @LoginUser String email
    ) {
        commentService.deleteComment(id, email);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, null));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> getCommentsByPost(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, comments));
    }

    // 특정 사용자가 작성한 모든 댓글 조회
    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getCommentsByUser(@LoginUser String email) {
        List<CommentResponseDto> comments = commentService.getCommentsByUser(email);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, comments));
    }

    // 댓글 ID로 댓글 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<ApiResponse> getCommentById(@PathVariable Long commentId) {
        CommentResponseDto comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, comment));
    }

    // 특정 게시물에 대한 특정 사용자의 모든 댓글 조회
    @GetMapping("/post/{postId}/user")
    public ResponseEntity<ApiResponse> getCommentsByPostAndUser(
            @PathVariable Long postId,
            @LoginUser String email
    ) {
        List<CommentResponseDto> comments = commentService.getCommentsByPostAndUser(postId, email);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, comments));
    }
}