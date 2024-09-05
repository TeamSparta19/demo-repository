package com.sparta.newsfeed19.entity;

import com.sparta.newsfeed19.post.dto.request.PostSaveRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor
public class Post extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String contents;

    // 게시물 만든 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Post(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public static Post from(PostSaveRequestDto postSaveRequestDto, User user) {
        return new Post(postSaveRequestDto.getTitle(), postSaveRequestDto.getContents(), user);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
