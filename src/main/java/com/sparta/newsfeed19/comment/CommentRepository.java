package com.sparta.newsfeed19.comment;


import com.sparta.newsfeed19.post.entity.Post;
import com.sparta.newsfeed19.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByUserId(long userId);

    List<Comment> findByUser(User user);

    List<Comment> findByPost(Post post);

    List<Comment> findByPostAndUser(Post post, User user);
}