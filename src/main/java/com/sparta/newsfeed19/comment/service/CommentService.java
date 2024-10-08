package com.sparta.newsfeed19.comment.service;

import com.sparta.newsfeed19.comment.dto.request.CommentRequestDto;
import com.sparta.newsfeed19.comment.dto.request.CommentUpdateRequestDto;
import com.sparta.newsfeed19.comment.dto.response.CommentResponseDto;
import com.sparta.newsfeed19.comment.repository.CommentRepository;
import com.sparta.newsfeed19.common.exception.ApiException;
import com.sparta.newsfeed19.common.exception.ResponseCode;
import com.sparta.newsfeed19.entity.Comment;
import com.sparta.newsfeed19.entity.Post;
import com.sparta.newsfeed19.entity.User;
import com.sparta.newsfeed19.post.repository.PostRepository;
import com.sparta.newsfeed19.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, String email) {
        if (!StringUtils.hasText(commentRequestDto.getContents())) {
            throw new ApiException(ResponseCode.EMPTY_COMMENT_CONTENT);
        }

        // 이메일을 통해 영속 상태의 사용자 엔티티 조회
        User persistedUser = userRepository.findActiveUserByEmail(email);
        log.info("Persisted User ID: {}", persistedUser.getId());

        // 영속 상태의 게시물 엔티티 조회
        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));
        log.info("Persisted Post ID: {}", post.getId());

        // 새로운 댓글 생성 및 저장
        Comment comment = Comment.from(persistedUser, post, commentRequestDto.getContents());
        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDto.from(savedComment);
    }

    // 댓글 수정
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto, String email) {
        if (!StringUtils.hasText(commentUpdateRequestDto.getContents())) {
            throw new ApiException(ResponseCode.EMPTY_COMMENT_CONTENT);
        }

        // 이메일을 통해 영속 상태의 사용자 엔티티 조회
        User persistedUser = userRepository.findActiveUserByEmail(email);

        // 댓글 조회
        Comment comment = commentRepository.findCommentById(commentId);

        // 권한 확인: 댓글 작성자와 로그인한 사용자가 동일한지 확인
        if (!comment.getUser().getId().equals(persistedUser.getId())) {
            throw new ApiException(ResponseCode.COMMENT_PERMISSION_DENIED);
        }

        // 댓글 내용 수정 메서드 사용
        comment.updateContents(commentUpdateRequestDto.getContents());
        Comment updatedComment = commentRepository.save(comment);

        return CommentResponseDto.from(updatedComment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, String email) {
        // 이메일을 통해 영속 상태의 사용자 엔티티 조회
        User persistedUser = userRepository.findActiveUserByEmail(email);

        // 댓글 조회
        Comment comment = commentRepository.findCommentById(commentId);

        // 권한 확인: 댓글 작성자와 로그인한 사용자가 동일한지 확인
        if (!comment.getUser().getId().equals(persistedUser.getId())) {
            throw new ApiException(ResponseCode.COMMENT_PERMISSION_DENIED);
        }

        // 댓글 삭제
        commentRepository.delete(comment);
    }

    // 특정 게시물에 대한 모든 댓글 조회
    @Transactional(readOnly = true) // 조회 메서드에만 readOnly 적용
    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream().map(CommentResponseDto::from).toList();
    }

    // 특정 사용자가 작성한 모든 댓글 조회
    @Transactional(readOnly = true) // 조회 메서드에만 readOnly 적용
    public List<CommentResponseDto> getCommentsByUser(String email) {
        User user = userRepository.findActiveUserByEmail(email);

        List<Comment> comments = commentRepository.findByUser(user);

        return comments.stream().map(CommentResponseDto::from).toList();
    }

    // 댓글 ID로 댓글 조회
    @Transactional(readOnly = true) // 조회 메서드에만 readOnly 적용
    public CommentResponseDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findCommentById(commentId);

        return CommentResponseDto.from(comment);
    }

    // 특정 게시물에 대한 특정 사용자의 모든 댓글 조회
    @Transactional(readOnly = true) // 조회 메서드에만 readOnly 적용
    public List<CommentResponseDto> getCommentsByPostAndUser(Long postId, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        User user = userRepository.findActiveUserByEmail(email);

        List<Comment> comments = commentRepository.findByPostAndUser(post, user);

        return comments.stream().map(CommentResponseDto::from).toList();
    }

}