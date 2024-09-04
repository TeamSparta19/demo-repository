package com.sparta.newsfeed19.user;

import com.sparta.newsfeed19.global.common.response.ApiResponse;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.user.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    //유저 등록(회원가입)
    @PostMapping("/users")
    public ResponseEntity<?> saveUser(@Valid @RequestBody SaveUserRequestDto saveUserRequestDto) {
        SaveUserResponseDto saveUserResponseDto = userService.saveUser(saveUserRequestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, saveUserResponseDto));
    }

    //유저 로그인
    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto) {
        String bearerToken = userService.login(requestDto);

        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, bearerToken));
    }

    // 유저 조회
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, userService.getUser(id)));
    }

    @GetMapping("/users/get")
    public String getUserInfo(HttpServletRequest request) {
        log.info("유저페이지 호출");
        return "유저페이지 리소스가 허가되었습니다";
    }


    // 유저 수정
    @PatchMapping("users/{id}")
    public ResponseEntity<?> updateUserPassword(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserPasswordRequestDto updateUserRequestDto) {
        UpdateUserPasswordResponseDto updateUserPasswordResponseDto = userService.updateUserPassword(id, updateUserRequestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, "Null"));
    }

}
