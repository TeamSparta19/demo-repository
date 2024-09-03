package com.sparta.newsfeed19.user;

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

    @PostMapping("/users")
    public ResponseEntity<?> saveUser(@Valid @RequestBody SaveUserRequestDto saveUserRequestDto) {
        SaveUserResponseDto saveUserResponseDto = userService.saveUser(saveUserRequestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, saveUserResponseDto));
    }

    @PostMapping("/users/login")
    public String login(@RequestBody LoginRequestDto requestDto) {
        try {
            userService.login(requestDto);
        } catch (Exception e) {
           return "redirect:/user/login?error=" + e.getMessage();
        }
        return "redirect:/";
    }

    @PatchMapping("users/{id}")
    public ResponseEntity<?> updateUserPassword(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserPasswordRequestDto updateUserRequestDto) {
        UpdateUserPasswordResponseDto updateUserPasswordResponseDto = userService.updateUserPassword(id, updateUserRequestDto);
        return ResponseEntity.ok(ApiResponse.setResponse(ResponseCode.SUCCESS, updateUserPasswordResponseDto));
    }
}
