package com.sparta.newsfeed19.comment.repository;

import com.sparta.newsfeed19.comment.Comment;
import com.sparta.newsfeed19.global.exception.ApiException;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.post.entity.Post;
import com.sparta.newsfeed19.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByUserId(long userId);

    List<Comment> findByUser(User user);

    List<Comment> findByPost(Post post);

    List<Comment> findByPostAndUser(Post post, User user);

    default Comment findCommentById(long commentId) {
        return findById(commentId)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_COMMENT));
    }
}