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
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional // 클래스 레벨에 @Transactional을 적용하여 모든 메서드에 기본 트랜잭션 설정
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
        User persistedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_USER));
        log.info("Persisted User ID: {}", persistedUser.getId());  // System.out.println 대신 log 사용

        // 영속 상태의 게시물 엔티티 조회
        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));
        log.info("Persisted Post ID: {}", post.getId());  // System.out.println 대신 log 사용

        // 새로운 댓글 생성 및 저장
        Comment comment = new Comment(persistedUser, post, commentRequestDto.getContents());
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getCommentId(),
                savedComment.getUser().getEmail(),
                savedComment.getContents(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt()
        );
    }

    // 댓글 수정
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto, String email) {
        if (!StringUtils.hasText(commentUpdateRequestDto.getContents())) {
            throw new ApiException(ResponseCode.EMPTY_COMMENT_CONTENT);
        }

        // 이메일을 통해 영속 상태의 사용자 엔티티 조회
        User persistedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_USER));

        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_COMMENT));

        // 권한 확인: 댓글 작성자와 로그인한 사용자가 동일한지 확인
        if (!comment.getUser().getId().equals(persistedUser.getId())) {
            throw new ApiException(ResponseCode.COMMENT_PERMISSION_DENIED);
        }

        // 댓글 내용 수정 메서드 사용
        comment.updateContents(commentUpdateRequestDto.getContents());
        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                updatedComment.getCommentId(),
                updatedComment.getUser().getEmail(),
                updatedComment.getContents(),
                updatedComment.getCreatedAt(),
                updatedComment.getUpdatedAt()
        );
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, String email) {
        // 이메일을 통해 영속 상태의 사용자 엔티티 조회
        User persistedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_USER));

        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_COMMENT));

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

        return comments.stream().map(comment -> new CommentResponseDto(
                comment.getCommentId(),
                comment.getUser().getEmail(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        )).collect(Collectors.toList());
    }

    // 특정 사용자가 작성한 모든 댓글 조회
    @Transactional(readOnly = true) // 조회 메서드에만 readOnly 적용
    public List<CommentResponseDto> getCommentsByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_USER));

        List<Comment> comments = commentRepository.findByUser(user);

        return comments.stream().map(comment -> new CommentResponseDto(
                comment.getCommentId(),
                comment.getUser().getEmail(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        )).collect(Collectors.toList());
    }

    // 댓글 ID로 댓글 조회
    @Transactional(readOnly = true) // 조회 메서드에만 readOnly 적용
    public CommentResponseDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_COMMENT));

        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getUser().getEmail(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    // 특정 게시물에 대한 특정 사용자의 모든 댓글 조회
    @Transactional(readOnly = true) // 조회 메서드에만 readOnly 적용
    public List<CommentResponseDto> getCommentsByPostAndUser(Long postId, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_USER));

        List<Comment> comments = commentRepository.findByPostAndUser(post, user);

        return comments.stream().map(comment -> new CommentResponseDto(
                comment.getCommentId(),
                comment.getUser().getEmail(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        )).collect(Collectors.toList());
    }
}