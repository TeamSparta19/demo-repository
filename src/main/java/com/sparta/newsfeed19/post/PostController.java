package com.sparta.newsfeed19.post;

import com.sparta.newsfeed19.post.postdto.PostRequsetDto;
import com.sparta.newsfeed19.post.postdto.PostResponseDto;
import com.sparta.newsfeed19.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feat/post")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final UserRepository userRepository;
//
//    @PostMapping
//    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequsetDto requsetDto){
//        PostResponseDto responseDto = postService.createPost(requsetDto);
//        responseDto new ResponseEntity<>(responseDto, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<>
}
