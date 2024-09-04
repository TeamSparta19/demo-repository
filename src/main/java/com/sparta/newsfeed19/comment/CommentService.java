package com.sparta.newsfeed19.comment;
import com.sparta.newsfeed19.comment.dto.CommentRequestDto;
import com.sparta.newsfeed19.comment.dto.CommentResponseDto;
import com.sparta.newsfeed19.comment.dto.CommentUpdateRequestDto;
import com.sparta.newsfeed19.global.exception.ApiException;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.post.entity.Post;
import com.sparta.newsfeed19.post.repository.PostRepository;
import com.sparta.newsfeed19.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // 댓글 생성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User loginUser) {
        if (!StringUtils.hasText(commentRequestDto.getContents())) {
            throw new ApiException(ResponseCode.EMPTY_COMMENT_CONTENT);
        }

        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        Comment comment = new Comment(loginUser, post, commentRequestDto.getContents());
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
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto, User loginUser) {
        if (!StringUtils.hasText(commentUpdateRequestDto.getContents())) {
            throw new ApiException(ResponseCode.EMPTY_COMMENT_CONTENT);
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_COMMENT));

        if (!comment.getUser().getId().equals(loginUser.getId())) {
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
    public void deleteComment(Long commentId, User loginUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_COMMENT));

        if (!comment.getUser().getId().equals(loginUser.getId())) {
            throw new ApiException(ResponseCode.COMMENT_PERMISSION_DENIED);
        }

        commentRepository.delete(comment);
    }
}