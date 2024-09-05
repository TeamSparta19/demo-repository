package com.sparta.newsfeed19.post.repository;

import com.sparta.newsfeed19.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 생성일자 기준 내림차순 순으로 정렬
    Page<Post> findAllByUserEmailIn(List<String> emails, Pageable pageable);
}
