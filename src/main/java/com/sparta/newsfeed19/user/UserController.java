package com.sparta.newsfeed19.user;

import com.sparta.newsfeed19.global.common.response.ApiResponse;
import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.user.dto.LoginRequestDto;
import com.sparta.newsfeed19.user.dto.SaveUserRequestDto;
import com.sparta.newsfeed19.user.dto.SaveUserResponseDto;
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

        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS,bearerToken));
    }

   // 유저 조회
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS,userService.getUser(id)));
    }

    @PatchMapping("users/{id}")
    public ResponseEntity<?> updateUserPassword(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserPasswordRequestDto updateUserRequestDto) {
        UpdateUserPasswordResponseDto updateUserPasswordResponseDto = userService.updateUserPassword(id, updateUserRequestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, updateUserPasswordResponseDto));
    }
}
