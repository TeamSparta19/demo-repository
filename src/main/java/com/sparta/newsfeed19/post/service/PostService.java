package com.sparta.newsfeed19.post.service;

import com.sparta.newsfeed19.common.exception.ApiException;
import com.sparta.newsfeed19.common.exception.ResponseCode;
import com.sparta.newsfeed19.entity.Follow;
import com.sparta.newsfeed19.entity.Post;
import com.sparta.newsfeed19.entity.User;
import com.sparta.newsfeed19.follow.repository.FollowRepository;
import com.sparta.newsfeed19.post.dto.request.PostSaveRequestDto;
import com.sparta.newsfeed19.post.dto.request.PostUpdateRequestDto;
import com.sparta.newsfeed19.post.dto.response.*;
import com.sparta.newsfeed19.post.repository.PostRepository;
import com.sparta.newsfeed19.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    // 게시물 저장 메서드
    @Transactional
    public PostSaveResponseDto savePost(PostSaveRequestDto postSaveRequestDto, String email) {
        User user = userRepository.findActiveUserByEmail(email);

        Post newPost = Post.from(postSaveRequestDto, user);

        Post savedPost = postRepository.save(newPost);

        return PostSaveResponseDto.from(savedPost);
    }

    // 게시물 단건 조회 메서드
    @Transactional
    public PostSimpleResponseDto getPost(Long postId, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        // 이메일로 현재 사용자 조회
        User currentUser = userRepository.findActiveUserByEmail(email);

        // 작성자 일치 여부 확인 (필요한 경우)
        if (!ObjectUtils.nullSafeEquals(currentUser.getId(), post.getUser().getId())) {
            throw new ApiException(ResponseCode.INVALID_REQUEST);
        }

        return PostSimpleResponseDto.from(post);
    }

    @Transactional
    public Page<PostDetailResponseDto> getPosts(Pageable pageable, String email) {
        // 현재 사용자의 이메일로 유저 정보 조회
        userRepository.existsActiveUserByEmail(email);

        // 사용자가 팔로우한 유저들의 이메일 목록을 가져옴
        List<Follow> follows = followRepository.findByFollowerEmail(email);
        List<String> followingEmails = new ArrayList<>();

        for (Follow follow : follows) {
            followingEmails.add(follow.getFollowing().getEmail());
        }

        // 사용자의 이메일도 리스트에 추가하여 자신이 올린 게시물도 조회되도록 설정
        followingEmails.add(email);

        // 사용자의 게시물 조회
        Page<Post> posts = postRepository.findAllByUserEmailIn(followingEmails, pageable);

        // Post 엔티티를 PostDetailResponseDto로 변환
        return PostDetailResponseDto.from(posts);
    }

    // 게시물 수정
    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        User currentUser = userRepository.findActiveUserByEmail(email);

        if (!ObjectUtils.nullSafeEquals(currentUser.getId(), post.getUser().getId())) {
            throw new ApiException(ResponseCode.INVALID_REQUEST);
        }

        post.update(
                postUpdateRequestDto.getTitle(),
                postUpdateRequestDto.getContents()
        );

        return PostUpdateResponseDto.from(post);
    }

    // 게시물 삭제
    public PostDeleteResponseDto deletePost(Long postId, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        User currentUser = userRepository.findActiveUserByEmail(email);

        if (!ObjectUtils.nullSafeEquals(currentUser.getId(), post.getUser().getId())) {
            throw new ApiException(ResponseCode.INVALID_REQUEST);
        }

        postRepository.deleteById(postId);

        return PostDeleteResponseDto.of(postId);
    }
}
