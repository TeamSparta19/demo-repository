package com.sparta.newsfeed19.user;

import com.sparta.newsfeed19.global.conpig.PasswordEncoder;
import com.sparta.newsfeed19.global.exception.ApiException;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.global.util.JwtUtil;
import com.sparta.newsfeed19.user.dto.LoginRequestDto;
import com.sparta.newsfeed19.user.dto.LoginResponseDto;
import com.sparta.newsfeed19.user.dto.SaveUserRequestDto;
import com.sparta.newsfeed19.user.dto.SaveUserResponseDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.newsfeed19.global.exception.ResponseCode.EXIST_EMAIL;

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
            throw new ApiException(EXIST_EMAIL);
        }

        User user = new User(
                saveUserRequestDto.getEmail(),
                passwordEncoder.encode(saveUserRequestDto.getPassword())
        );

        User savedUser = userRepository.save(user);

        return new SaveUserResponseDto(
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );
    }

    @Transactional
    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();



        // 사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("등록된 회원이 없습니다.")
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password,user.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }


        // JWT 생성 및 쿠키에 저장 후 Response 에 추가
        String token = jwtUtil.createToken(user.getEmail());
        jwtUtil.addJwtToCookie(token, res);

    }

    // JWT에서 이메일을 추출하여 현재 유저를 가져오는 메서드
    public User getCurrentUser(HttpServletRequest request) {
        // 요청 헤더에서 JWT 토큰을 가져옴
        String token = jwtUtil.substringToken(request.getHeader(JwtUtil.AUTHORIZATION_HEADER));

        // JWT 토큰에서 이메일을 추출
        String email = String.valueOf(jwtUtil.getUserInfoFromToken(token)); // 이메일이 직접 반환된다고 가정

        // 이메일로 사용자 조회
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ResponseCode.USER_NOT_FOUND));
    }
}
