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

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;






}