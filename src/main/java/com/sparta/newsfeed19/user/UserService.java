package com.sparta.newsfeed19.user;

import com.sparta.newsfeed19.user.dto.SimpleResponseDto;
import com.sparta.newsfeed19.global.config.PasswordEncoder;
import com.sparta.newsfeed19.global.exception.ApiException;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.global.util.JwtUtil;
import com.sparta.newsfeed19.user.dto.LoginRequestDto;
import com.sparta.newsfeed19.user.dto.SaveUserRequestDto;
import com.sparta.newsfeed19.user.dto.SaveUserResponseDto;
import com.sparta.newsfeed19.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.sparta.newsfeed19.global.exception.ResponseCode.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SaveUserResponseDto saveUser(SaveUserRequestDto saveUserRequestDto) {

        if (userRepository.existsByEmail(saveUserRequestDto.getEmail())) {
            throw new ApiException(ResponseCode.EXIST_EMAIL);
        }

        User user = new User(
                saveUserRequestDto.getEmail(),
                passwordEncoder.encode(saveUserRequestDto.getPassword()),
                null
        );

        User savedUser = userRepository.save(user);

        return new SaveUserResponseDto(
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );
    }

    //유저 로그인
    @Transactional
    public String login(LoginRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();


        // 사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("등록된 회원이 없습니다.")
        );

        // 비밀번호 확인 인코딩 버전
        if (!passwordEncoder.matches(password,user.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 에 추가
        String token = jwtUtil.createToken(user.getEmail());

        return token;
    }

    //유저 조회
    public SimpleResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ApiException(ResponseCode.NOT_FOUND_USER));
        return new SimpleResponseDto(user);
    }

    @Transactional
    public void updateUserPassword(
            Long userId,
            UpdateUserPasswordRequestDto updateUserRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(NOT_FOUND_USER));


        // 비밀번호 수정 시, 본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우
        if (!passwordEncoder.matches(updateUserRequestDto.getOldPassword(), user.getPassword())) {
            throw new ApiException(INVALID_PASSWORD);
        }
        //현재 비밀번호와 동일한 비밀번호로 수정하는 경우
        if (passwordEncoder.matches(updateUserRequestDto.getNewPassword(), user.getPassword())) {
            throw new ApiException(SAME_PASSWORD);
        }

        user.updatePassword(passwordEncoder.encode(updateUserRequestDto.getNewPassword()));
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long id, DeleteUserRequestDto deleteUserRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(NOT_FOUND_USER));

        if (!passwordEncoder.matches(deleteUserRequestDto.getPassword(), user.getPassword())) {
            throw new ApiException(INVALID_PASSWORD);
        }

        user.updateDeleteAt(user.getDeletedAt());

         userRepository.save(user);
    }
}
