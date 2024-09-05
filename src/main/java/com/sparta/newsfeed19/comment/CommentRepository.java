package com.sparta.newsfeed19.comment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdAndDeletedAtIsNull(Long postId);  // 특정 게시물의 삭제되지 않은 모든 댓글 조회

    List<Comment> findAllByUserEmailAndDeletedAtIsNull(String email);  // 특정 사용자의 삭제되지 않은 모든 댓글 조회

    List<Comment> findAllByPostIdAndUserEmailAndDeletedAtIsNull(Long postId, String email);  // 특정 게시물의 특정 사용자가 작성한 삭제되지 않은 댓글 조회
}
