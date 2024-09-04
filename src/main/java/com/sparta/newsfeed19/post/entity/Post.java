package com.sparta.newsfeed19.post.entity;

import com.sparta.newsfeed19.global.common.entity.TimeStamp;
import com.sparta.newsfeed19.user.User;
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
    private String title;
    private String contents;

    // 게시물 만든 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

//    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
//    private List<Comment> comments = new ArrayList<>();

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
