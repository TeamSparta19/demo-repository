package com.sparta.newsfeed19.comment;


import com.sparta.newsfeed19.post.entity.Post;
import com.sparta.newsfeed19.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "COMMENT")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long commentId;  // 댓글 고유번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 작성자 (User 엔티티와의 관계 설정)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // 게시물 (Post 엔티티와의 관계 설정)

    @Column(name = "contents", nullable = false)
    private String contents;  // 댓글 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // 생성일

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;  // 수정일

    // 엔티티 저장 전 자동으로 호출되는 메서드
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 엔티티 업데이트 전 자동으로 호출되는 메서드
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 모든 필드를 받는 생성자
    public Comment(User user, Post post, String contents) {
        this.user = user;
        this.post = post;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}