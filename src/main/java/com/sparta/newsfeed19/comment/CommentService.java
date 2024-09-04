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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;




@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, String userEmail) {
        if (!StringUtils.hasText(commentRequestDto.getContents())) {
            throw new ApiException(ResponseCode.EMPTY_COMMENT_CONTENT);
        }

        // 게시물 조회
        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        // 댓글 생성
        Comment comment = new Comment(userEmail, post, commentRequestDto.getContents());
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getCommentId(),
                savedComment.getUserEmail(),  // 변경된 부분
                savedComment.getContents(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt()
        );
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto, String userEmail) {
        if (!StringUtils.hasText(commentUpdateRequestDto.getContents())) {
            throw new ApiException(ResponseCode.EMPTY_COMMENT_CONTENT);
        }

        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_COMMENT));

        // 권한 확인: 댓글 작성자와 로그인한 사용자가 동일한지 확인
        if (!comment.getUserEmail().equals(userEmail)) {
            throw new ApiException(ResponseCode.COMMENT_PERMISSION_DENIED);
        }

        // 댓글 내용 수정
        comment.updateContents(commentUpdateRequestDto.getContents());
        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                updatedComment.getCommentId(),
                updatedComment.getUserEmail(),
                updatedComment.getContents(),
                updatedComment.getCreatedAt(),
                updatedComment.getUpdatedAt()
        );
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, String userEmail) {
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_COMMENT));

        // 권한 확인: 댓글 작성자와 로그인한 사용자가 동일한지 확인
        if (!comment.getUserEmail().equals(userEmail)) {
            throw new ApiException(ResponseCode.COMMENT_PERMISSION_DENIED);
        }

        // 댓글 삭제
        commentRepository.delete(comment);
    }
}