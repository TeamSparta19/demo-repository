package com.sparta.newsfeed19.user;

import com.sparta.newsfeed19.global.annotation.LoginUser;
import com.sparta.newsfeed19.global.common.response.ApiResponse;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 유저 등록(회원가입)
    @PostMapping("/users")
    public ResponseEntity<?> saveUser(@Valid @RequestBody SaveUserRequestDto saveUserRequestDto) {
        SaveUserResponseDto saveUserResponseDto = userService.saveUser(saveUserRequestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, saveUserResponseDto));
    }

    // 유저 로그인
    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto) {
        String bearerToken = userService.login(requestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, bearerToken));
    }

    // 유저 조회
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        GetUserResponseDto getUserResponseDto = userService.getUser(id);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, getUserResponseDto));
    }

    // 유저 수정
    @PatchMapping("users/{id}")
    public ResponseEntity<?> updateUserPassword(
            @LoginUser String email,
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserPasswordRequestDto updateUserRequestDto) {
        userService.updateUserPassword(email, id, updateUserRequestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, null));
    }

    // 유저 삭제
    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(
            @LoginUser String email,
            @PathVariable Long id,
            @RequestBody DeleteUserRequestDto deleteUserRequestDto) {
        userService.deleteUser(email, id, deleteUserRequestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, null));
    }

}
