package com.sparta.newsfeed19.user.service;

import com.sparta.newsfeed19.config.PasswordEncoder;
import com.sparta.newsfeed19.common.exception.ApiException;
import com.sparta.newsfeed19.common.exception.ResponseCode;
import com.sparta.newsfeed19.common.util.JwtUtil;
import com.sparta.newsfeed19.entity.User;
import com.sparta.newsfeed19.user.UserRequest;
import com.sparta.newsfeed19.user.dto.request.DeleteUserRequestDto;
import com.sparta.newsfeed19.user.dto.request.LoginRequestDto;
import com.sparta.newsfeed19.user.dto.request.SaveUserRequestDto;
import com.sparta.newsfeed19.user.dto.request.UpdateUserPasswordRequestDto;
import com.sparta.newsfeed19.user.dto.response.GetUserResponseDto;
import com.sparta.newsfeed19.user.dto.response.SaveUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.newsfeed19.common.exception.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRequest userRequest;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 유저 등록
    @Transactional
    public SaveUserResponseDto saveUser(SaveUserRequestDto saveUserRequestDto) {
        if (userRequest.existsUser(saveUserRequestDto.getEmail())) {
            throw new ApiException(ResponseCode.EXIST_EMAIL);
        }

        String password = passwordEncoder.encode(saveUserRequestDto.getPassword());

        User user = User.of(saveUserRequestDto.getEmail(), password);

        User savedUser = userRequest.saveUser(user);

        return SaveUserResponseDto.from(savedUser);
    }

    // 유저 로그인
    @Transactional
    public String login(LoginRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        // 사용자 확인
        User user = userRequest.findUser(email);

        // 비밀번호 확인 (인코딩)
        isValidPassword(password, user.getPassword());

        // JWT 생성 및 쿠키에 저장 후 Response 에 추가
        return jwtUtil.createToken(user.getEmail());
    }

    // 유저 조회
    public GetUserResponseDto getUser(Long id) {
        User user = userRequest.findUser(id);
        return GetUserResponseDto.of(user);
    }

    // 유저 수정
    @Transactional
    public void updateUserPassword(
            String email,
            Long id,
            UpdateUserPasswordRequestDto updateUserRequestDto
    ) {
        User user = userRequest.findUser(email);

        if (!user.getId().equals(id)) {
            throw new ApiException(UNAUTHORIZED_UPDATE_USER);
        }

        // 비밀번호 수정 시, 본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우
        isValidPassword(updateUserRequestDto.getOldPassword(), user.getPassword());

        // 현재 비밀번호와 동일한 비밀번호로 수정하는 경우
        if (passwordEncoder.matches(updateUserRequestDto.getNewPassword(), user.getPassword())) {
            throw new ApiException(SAME_PASSWORD);
        }

        user.updatePassword(passwordEncoder.encode(updateUserRequestDto.getNewPassword()));
        userRequest.saveUser(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(String email, Long id, DeleteUserRequestDto deleteUserRequestDto) {
        User user = userRequest.findUser(email);

        if (!user.getId().equals(id)) {
            throw new ApiException(UNAUTHORIZED_DELETE_USER);
        }

        isValidPassword(deleteUserRequestDto.getPassword(), user.getPassword());

        user.updateDeleteAt();
        userRequest.delete(user);
    }

    private void isValidPassword(String inputPassword, String realPassword) {
        if (!passwordEncoder.matches(inputPassword, realPassword)) {
            throw new ApiException(INVALID_PASSWORD);
        }
    }
}
