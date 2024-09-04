package com.sparta.newsfeed19.comment;

import com.sparta.newsfeed19.comment.dto.CommentRequestDto;
import com.sparta.newsfeed19.comment.dto.CommentResponseDto;
import com.sparta.newsfeed19.comment.dto.CommentUpdateRequestDto;
import com.sparta.newsfeed19.global.common.response.ApiResponse;
import com.sparta.newsfeed19.global.annotation.LoginUser;
import com.sparta.newsfeed19.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 등록
    @PostMapping
    public ResponseEntity<ApiResponse> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                                     @LoginUser User loginUser) {
        CommentResponseDto createdComment = commentService.createComment(commentRequestDto, loginUser);
        return ResponseEntity.ok(new ApiResponse(200, "정상 처리되었습니다.", createdComment));
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateComment(@PathVariable Long id,
                                                     @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,
                                                     @LoginUser User loginUser) {
        CommentResponseDto updatedComment = commentService.updateComment(id, commentUpdateRequestDto, loginUser);
        return ResponseEntity.ok(new ApiResponse(200, "정상 처리되었습니다.", updatedComment));
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long id,
                                                     @LoginUser User loginUser) {
        commentService.deleteComment(id, loginUser);
        return ResponseEntity.ok(new ApiResponse(200, "정상 처리되었습니다.", null));
    }
}