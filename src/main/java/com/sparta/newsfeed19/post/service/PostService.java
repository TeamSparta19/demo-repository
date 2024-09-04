package com.sparta.newsfeed19.post.service;

import com.sparta.newsfeed19.global.exception.ApiException;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.post.dto.request.PostSaveRequestDto;
import com.sparta.newsfeed19.post.dto.request.PostUpdateRequestDto;
import com.sparta.newsfeed19.post.dto.response.*;
import com.sparta.newsfeed19.post.entity.Post;
import com.sparta.newsfeed19.post.repository.PostRepository;
import com.sparta.newsfeed19.user.User;
import com.sparta.newsfeed19.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시물 저장 메서드
    @Transactional
    public PostSaveResponseDto savePost(PostSaveRequestDto postSaveRequestDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_USER));
        Post newPost = new Post(
                postSaveRequestDto.getTitle(),
                postSaveRequestDto.getContents(),
                user
        );
        Post savedPost = postRepository.save(newPost);

        return new PostSaveResponseDto(
                savedPost.getId(),
                user,
                savedPost.getTitle(),
                savedPost.getContents(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt()
        );
    }

    // 게시물 단건 조회 메서드
    @Transactional
    public PostSimpleResponseDto getPost(Long postId, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        // 이메일로 현재 사용자 조회
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_USER));

        // 작성자 일치 여부 확인 (필요한 경우)
        if(!ObjectUtils.nullSafeEquals(currentUser.getId(), post.getUser().getId())) {
            throw new ApiException(ResponseCode.INVALID_REQUEST);
        }

        return new PostSimpleResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    // 게시물 다건 조회 메서드
    @Transactional
    public Page<PostDetailResponseDto> getPosts(int page, int size, String email) {
        Pageable pageable = PageRequest.of(page - 1, size);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_USER));

        // 사용자의 게시물 조회
        Page<Post> posts = postRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId(), pageable);

        // Post 엔티티를 PostDetailResponseDto로 변환
        return posts.map(post -> new PostDetailResponseDto(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        ));
    }

    // 게시물 수정
    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_USER));

        if(!ObjectUtils.nullSafeEquals(currentUser.getId(), post.getUser().getId())) {
            throw new ApiException(ResponseCode.INVALID_REQUEST);
        }

        post.update(
                postUpdateRequestDto.getTitle(),
                postUpdateRequestDto.getContents()
        );

        return new PostUpdateResponseDto(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    // 게시물 삭제
    public PostDeleteResponseDto deletePost(Long postId, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.NOT_FOUND_USER));

        if(!ObjectUtils.nullSafeEquals(currentUser.getId(), post.getUser().getId())) {
            throw new ApiException(ResponseCode.INVALID_REQUEST);
        }

        postRepository.deleteById(postId);

        return new PostDeleteResponseDto(
                post.getId()
        );
    }
}
