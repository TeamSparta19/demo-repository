package com.sparta.newsfeed19.comment;

import com.sparta.newsfeed19.comment.dto.CommentRequestDto;
import com.sparta.newsfeed19.comment.dto.CommentResponseDto;
import com.sparta.newsfeed19.comment.dto.CommentUpdateRequestDto;
import com.sparta.newsfeed19.global.common.response.ApiResponse;
import com.sparta.newsfeed19.global.annotation.LoginUser;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}