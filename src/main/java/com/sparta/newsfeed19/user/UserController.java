package com.sparta.newsfeed19.user;

import com.sparta.newsfeed19.user.dto.LoginRequestDto;
import com.sparta.newsfeed19.user.dto.LoginResponseDto;
import com.sparta.newsfeed19.user.dto.SaveUserRequestDto;
import com.sparta.newsfeed19.user.dto.SaveUserResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<SaveUserResponseDto> saveUser(@RequestBody SaveUserRequestDto saveUserRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok(userService.saveUser(saveUserRequestDto));
    }

    @PostMapping("/users/login")
    public String login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        try {
            userService.login(requestDto, res);
        } catch (Exception e) {
           return "redirect:/user/login?error=" + e.getMessage();
        }
        return "redirect:/";
    }
}
