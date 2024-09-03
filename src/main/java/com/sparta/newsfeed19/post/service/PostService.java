package com.sparta.newsfeed19.post.service;

import com.sparta.newsfeed19.global.exception.ApiException;
import com.sparta.newsfeed19.global.exception.ResponseCode;

import com.sparta.newsfeed19.post.dto.request.*;
import com.sparta.newsfeed19.post.dto.response.*;

import com.sparta.newsfeed19.post.entity.Post;
import com.sparta.newsfeed19.post.repository.PostRepository;
import com.sparta.newsfeed19.user.User;
import com.sparta.newsfeed19.user.UserRepository;
import com.sparta.newsfeed19.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    // 게시물 등록 메서드
    @Transactional
    public PostSaveResponseDto savePost(PostSaveRequestDto postSaveRequestDto) {

        // User 엔티티를 가져오는 부분 확인
        User user = userRepository.findById(postSaveRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ResponseCode.USER_NOT_FOUND));

        System.out.println("User ID from request DTO: " + postSaveRequestDto.getUserId());
        System.out.println("Fetched User: " + user);
        // Post 엔티티 생성 및 저장
        Post newPost = new Post(
                user,
                postSaveRequestDto.getTitle(),
                postSaveRequestDto.getContents()
        );
       Post savePost = postRepository.save(newPost);

       // PostSaveResponseDto 생성 시 UserDto로 전환
        return new PostSaveResponseDto(
                savePost.getId(),
                user,
                savePost.getTitle(),
                savePost.getContents(),
                savePost.getCreatedAt(),
                savePost.getUpdatedAt()
        );
    }

    // 게시물 다건 조회 메서드(이메일 기반)
    @Transactional
    public Page<PostDetailResponseDto> getPosts(int page, int size, String email) {
        // 페이지 번호가 1보다 작다면 기본값 1로 설정
        if (page < 1) {
            page = 1;
        }

        // 페이지 크기가 1보다 작다면 기본값 10으로 설정
        if (size < 1) {
            size = 10;
        }
        
        Pageable pageable = PageRequest.of(page - 1, size);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.USER_NOT_FOUND));

        Page<Post> posts = postRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId(), pageable);

        return posts.map(post -> new PostDetailResponseDto(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        ));
    }

    // 게시물 단건 조회 메서드
    @Transactional
    public PostSimpleResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        return new PostSimpleResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    // 게시물 수정 메서드
    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));
        User user = userRepository.findById(postUpdateRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ResponseCode.USER_NOT_FOUND));

        // 작성자 일치 여부 null-safe 비교
        if (!ObjectUtils.nullSafeEquals(user.getId(), post.getUser().getId())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
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

    // 게시물 삭제 메서드
    @Transactional
    public void deletePost(Long postId, HttpServletRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ResponseCode.POST_NOT_FOUND));

        // 현제 사용자 확인
        User currentUser = userService.getCurrentUser(request);

        // 작성자 일치 여부 null-safe 비교
        if (!ObjectUtils.nullSafeEquals(currentUser.getId(), post.getUser().getId())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }

        postRepository.deleteById(postId);
    }
}
