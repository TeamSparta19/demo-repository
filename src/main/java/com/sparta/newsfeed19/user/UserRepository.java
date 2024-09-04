package com.sparta.newsfeed19.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndDeletedAtIsNull(long id);

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    boolean existsByEmail(String email);
}
