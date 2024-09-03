package com.sparta.newsfeed19.user;

import com.sparta.newsfeed19.baseentity.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="user")
public class User extends TimeStamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }







}