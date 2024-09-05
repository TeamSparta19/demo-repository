package com.sparta.newsfeed19.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "following", orphanRemoval = true)
    private List<Follow> followerList;

    @OneToMany(mappedBy = "follower", orphanRemoval = true)
    private List<Follow> followingList;

    private User(String email, String password) {
        this.email = email;
        this.password = password;
        this.deletedAt = null;
        this.followerList = new ArrayList<>();
        this.followingList = new ArrayList<>();
    }

    public static User of(String email, String password) {
        return new User(email, password);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateDeleteAt() {
        this.deletedAt = LocalDateTime.now();
    }
}
