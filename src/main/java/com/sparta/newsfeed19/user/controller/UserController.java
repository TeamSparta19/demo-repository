package com.sparta.newsfeed19.user.controller;

import com.sparta.newsfeed19.common.annotation.LoginUser;
import com.sparta.newsfeed19.common.response.ApiResponse;
import com.sparta.newsfeed19.common.exception.ResponseCode;
import com.sparta.newsfeed19.user.dto.request.DeleteUserRequestDto;
import com.sparta.newsfeed19.user.dto.request.LoginRequestDto;
import com.sparta.newsfeed19.user.dto.request.SaveUserRequestDto;
import com.sparta.newsfeed19.user.dto.request.UpdateUserPasswordRequestDto;
import com.sparta.newsfeed19.user.dto.response.GetUserResponseDto;
import com.sparta.newsfeed19.user.dto.response.SaveUserResponseDto;
import com.sparta.newsfeed19.user.service.UserService;
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
    public ResponseEntity<ApiResponse> saveUser(@Valid @RequestBody SaveUserRequestDto saveUserRequestDto) {
        SaveUserResponseDto saveUserResponseDto = userService.saveUser(saveUserRequestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, saveUserResponseDto));
    }

    // 유저 로그인
    @PostMapping("/users/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequestDto requestDto) {
        String bearerToken = userService.login(requestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, bearerToken));
    }

    // 유저 조회
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long id) {
        GetUserResponseDto getUserResponseDto = userService.getUser(id);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, getUserResponseDto));
    }

    // 유저 수정
    @PatchMapping("users/{id}")
    public ResponseEntity<ApiResponse> updateUserPassword(
            @LoginUser String email,
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserPasswordRequestDto updateUserRequestDto) {
        userService.updateUserPassword(email, id, updateUserRequestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, null));
    }

    // 유저 삭제
    @DeleteMapping("users/{id}")
    public ResponseEntity<ApiResponse> deleteUser(
            @LoginUser String email,
            @PathVariable Long id,
            @RequestBody DeleteUserRequestDto deleteUserRequestDto) {
        userService.deleteUser(email, id, deleteUserRequestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, null));
    }

}
