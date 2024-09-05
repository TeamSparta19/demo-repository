package com.sparta.newsfeed19.comment;


import com.sparta.newsfeed19.global.common.entity.TimeStamp;
import com.sparta.newsfeed19.post.entity.Post;
import com.sparta.newsfeed19.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String contents;

    public Comment(User user, Post post, String contents) {
        this.user = user;
        this.post = post;
        this.contents = contents;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }
}