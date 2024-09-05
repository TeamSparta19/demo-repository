package com.sparta.newsfeed19.comment;

import com.sparta.newsfeed19.comment.dto.CommentRequestDto;
import com.sparta.newsfeed19.comment.dto.CommentResponseDto;
import com.sparta.newsfeed19.comment.dto.CommentUpdateRequestDto;
import com.sparta.newsfeed19.global.common.response.ApiResponse;
import com.sparta.newsfeed19.global.annotation.LoginUser;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.user.User;
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
    public ResponseEntity<ApiResponse> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                                     @LoginUser String email) {  // 이메일로 변경
        CommentResponseDto createdComment = commentService.createComment(commentRequestDto, email);
        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS.getCode().value(), ResponseCode.SUCCESS.getMessage(), createdComment));
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateComment(@PathVariable Long id,
                                                     @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,
                                                     @LoginUser String email) {  // 이메일로 변경
        CommentResponseDto updatedComment = commentService.updateComment(id, commentUpdateRequestDto, email);
        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS.getCode().value(), ResponseCode.SUCCESS.getMessage(), updatedComment));
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long id,
                                                     @LoginUser String email) {  // 이메일로 변경
        commentService.deleteComment(id, email);
        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS.getCode().value(), ResponseCode.SUCCESS.getMessage(), null));
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> getCommentsByPost(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS.getCode().value(), ResponseCode.SUCCESS.getMessage(), comments));
    }

    // 특정 사용자가 작성한 모든 댓글 조회
    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getCommentsByUser(@LoginUser String email) {
        List<CommentResponseDto> comments = commentService.getCommentsByUser(email);
        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS.getCode().value(), ResponseCode.SUCCESS.getMessage(), comments));
    }

    // 댓글 ID로 댓글 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<ApiResponse> getCommentById(@PathVariable Long commentId) {
        CommentResponseDto comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS.getCode().value(), ResponseCode.SUCCESS.getMessage(), comment));
    }

    // 특정 게시물에 대한 특정 사용자의 모든 댓글 조회
    @GetMapping("/post/{postId}/user")
    public ResponseEntity<ApiResponse> getCommentsByPostAndUser(@PathVariable Long postId, @LoginUser String email) {
        List<CommentResponseDto> comments = commentService.getCommentsByPostAndUser(postId, email);
        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS.getCode().value(), ResponseCode.SUCCESS.getMessage(), comments));
    }
}