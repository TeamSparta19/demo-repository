package com.sparta.newsfeed19.user;

import com.sparta.newsfeed19.follow.Follow;
import com.sparta.newsfeed19.global.common.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "following", orphanRemoval = true)
    private List<Follow> followerList;

    @OneToMany(mappedBy = "follower", orphanRemoval = true)
    private List<Follow> followingList;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
