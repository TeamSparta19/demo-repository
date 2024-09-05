package com.sparta.newsfeed19.user;

import com.sparta.newsfeed19.comment.repository.CommentRepository;
import com.sparta.newsfeed19.follow.FollowRepository;
import com.sparta.newsfeed19.global.exception.ApiException;
import com.sparta.newsfeed19.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.sparta.newsfeed19.global.exception.ResponseCode.NOT_FOUND_USER;

@Component
@RequiredArgsConstructor
public class UserRequest {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final FollowRepository followRepository;

    public boolean existsUser(String email) {
        return userRepository.existsByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findUser(String email) {
        return userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER));
    }

    public User findUser(long id) {
        return userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER));
    }

    public void delete(User user) {
        userRepository.save(user);
        postRepository.deleteByUserId(user.getId());
        commentRepository.deleteByUserId(user.getId());
        followRepository.deleteByFollowerId(user.getId());
        followRepository.deleteByFollowingId(user.getId());
    }
}
