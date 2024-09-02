package com.sparta.newsfeed19.user;

import com.sparta.newsfeed19.user.dto.LoginRequestDto;
import com.sparta.newsfeed19.user.dto.LoginResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;


    @PostMapping("/users/login")
    public String login(LoginRequestDto requestDto, HttpServletResponse res) {
        try {
            userService.login(requestDto, res);
        } catch (Exception e) {
           return "redirect:/user/login?error=" + e.getMessage();
        }
        return "redirect:/";
    }
}
