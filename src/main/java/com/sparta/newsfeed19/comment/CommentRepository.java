package com.sparta.newsfeed19.comment;


import com.sparta.newsfeed19.post.entity.Post;
import com.sparta.newsfeed19.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByUserEmailAndDeletedAtIsNull(String email);  // 특정 사용자의 삭제되지 않은 모든 댓글 조회

    List<Comment> findAllByPostIdAndUserEmailAndDeletedAtIsNull(Long postId, String email);  // 특정 게시물의 특정 사용자가 작성한 삭제되지 않은 댓글 조회

    void deleteByUserId(long userId);

    List<Comment> findByUser(User user);

    List<Comment> findByPost(Post post);

    List<Comment> findByPostAndUser(Post post, User user);
}