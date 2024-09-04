package com.sparta.newsfeed19.follow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    void deleteByFollowerIdAndFollowingId(long followerId, long followingId);
    List<Follow> findByFollowingEmail(String email);
    List<Follow> findByFollowerEmail(String email);
}
