package com.sparta.newsfeed19.comment;


import com.sparta.newsfeed19.global.common.entity.TimeStamp;
import com.sparta.newsfeed19.post.entity.Post;
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

    // 사용자 이메일을 저장
    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String contents;

    public Comment(String userEmail, Post post, String contents) {
        this.userEmail = userEmail;
        this.post = post;
        this.contents = contents;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }
}