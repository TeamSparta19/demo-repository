package com.sparta.newsfeed19.comment;
import com.sparta.newsfeed19.comment.dto.CommentRequestDto;
import com.sparta.newsfeed19.comment.dto.CommentResponseDto;
import com.sparta.newsfeed19.comment.dto.CommentUpdateRequestDto;
import com.sparta.newsfeed19.global.exception.ApiException;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.post.entity.Post;
import com.sparta.newsfeed19.post.repository.PostRepository;
import com.sparta.newsfeed19.user.User;
import com.sparta.newsfeed19.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // 댓글 생성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User loginUser) {
        // 댓글 내용이 비어 있는지 확인
        if (!StringUtils.hasText(commentRequestDto.getContents())) {
            throw new ApiException(ResponseCode.EMPTY_COMMENT_CONTENT);
        }

        // 사용자와 게시물 조회
        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        try {
            // 댓글 생성
            Comment comment = new Comment(loginUser, post, commentRequestDto.getContents());
            Comment savedComment = commentRepository.save(comment);

            // Response DTO 반환
            return new CommentResponseDto(
                    savedComment.getCommentId(),
                    savedComment.getUser().getEmail(),
                    savedComment.getContents(),
                    savedComment.getCreatedAt(),
                    savedComment.getUpdatedAt()
            );
        } catch (Exception e) {
            throw new ApiException(ResponseCode.COMMENT_CREATION_FAILED);
        }
    }

    // 댓글 수정
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto, User loginUser) {
        // 댓글 내용이 비어 있는지 확인
        if (!StringUtils.hasText(commentUpdateRequestDto.getContents())) {
            throw new ApiException(ResponseCode.EMPTY_COMMENT_CONTENT);
        }

        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_COMMENT));

        // 사용자 권한 확인
        if (!comment.getUser().getId().equals(loginUser.getId())) {
            throw new ApiException(ResponseCode.COMMENT_PERMISSION_DENIED);
        }

        try {
            // 댓글 내용 업데이트
            comment.setContents(commentUpdateRequestDto.getContents());
            comment.setUpdatedAt(LocalDateTime.now());
            Comment updatedComment = commentRepository.save(comment);

            // Response DTO 반환
            return new CommentResponseDto(
                    updatedComment.getCommentId(),
                    updatedComment.getUser().getEmail(),
                    updatedComment.getContents(),
                    updatedComment.getCreatedAt(),
                    updatedComment.getUpdatedAt()
            );
        } catch (Exception e) {
            throw new ApiException(ResponseCode.COMMENT_UPDATE_FAILED);
        }
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, User loginUser) {
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_COMMENT));

        // 사용자 권한 확인
        if (!comment.getUser().getId().equals(loginUser.getId())) {
            throw new ApiException(ResponseCode.COMMENT_PERMISSION_DENIED);
        }

        commentRepository.delete(comment);
    }
}