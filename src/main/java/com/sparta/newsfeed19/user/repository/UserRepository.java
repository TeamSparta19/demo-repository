package com.sparta.newsfeed19.user.repository;

import com.sparta.newsfeed19.common.exception.ApiException;
import com.sparta.newsfeed19.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.sparta.newsfeed19.common.exception.ResponseCode.NOT_FOUND_USER;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndDeletedAtIsNull(long id);

    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndDeletedAtIsNull(String email);

    default User findActiveUserByEmail(String email) {
        return findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER));
    }

    default User findActiveUserById(long id) {
        return findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER));
    }

    default void existsActiveUserByEmail(String email) {
        if (!existsByEmailAndDeletedAtIsNull(email)) {
            throw new ApiException(NOT_FOUND_USER);
        }
    }
}
