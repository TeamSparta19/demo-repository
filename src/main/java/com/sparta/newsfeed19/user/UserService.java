package com.sparta.newsfeed19.user;

import com.sparta.newsfeed19.global.conpig.PasswordEncoder;
import com.sparta.newsfeed19.global.util.JwtUtil;
import com.sparta.newsfeed19.user.dto.LoginRequestDto;
import com.sparta.newsfeed19.user.dto.LoginResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
}
