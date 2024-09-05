package com.sparta.newsfeed19.follow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.newsfeed19.follow.controller.FollowController;
import com.sparta.newsfeed19.follow.dto.request.FollowRequestDto;
import com.sparta.newsfeed19.follow.dto.response.FollowerResponseDto;
import com.sparta.newsfeed19.follow.dto.response.FollowingResponseDto;
import com.sparta.newsfeed19.follow.service.FollowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FollowController.class)
@ExtendWith(SpringExtension.class)
class FollowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FollowService followService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("팔로우에 성공한다.")
    public void follow_success() throws Exception {
        // given
        String email = "test@test.com";
        FollowRequestDto followRequestDto = new FollowRequestDto(1L);

        willDoNothing().given(followService).follow(email, followRequestDto);

        // when, then
        mockMvc.perform(post("/api/following")
                        .content(asJsonString(followRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("정상 처리되었습니다"));
    }

    @Test
    @DisplayName("팔로우할 유저가 명시되지 않아 팔로우에 실패한다.")
    public void follow_validation_failure() throws Exception {
        // given
        String email = "test@test.com";
        FollowRequestDto followRequestDto = new FollowRequestDto(null);

        willDoNothing().given(followService).follow(email, followRequestDto);

        // when, then
        mockMvc.perform(post("/api/following")
                        .content(asJsonString(followRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("언팔로우에 성공한다.")
    public void unfollow_success() throws Exception {
        // given
        FollowRequestDto followRequestDto = new FollowRequestDto(1L);

        willDoNothing().given(followService).unfollow(anyString(), any(FollowRequestDto.class));

        // when, then
        mockMvc.perform(delete("/api/following")
                        .content(asJsonString(followRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("정상 처리되었습니다"));
    }

    @Test
    @DisplayName("언팔로우할 유저가 명시되지 않아 언팔로우에 실패한다.")
    public void unfollow_validation_failure() throws Exception {
        // given
        String email = "test@test.com";
        FollowRequestDto followRequestDto = new FollowRequestDto(null);

        willDoNothing().given(followService).follow(email, followRequestDto);

        // when, then
        mockMvc.perform(delete("/api/following")
                        .content(asJsonString(followRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("팔로워 목록 조회에 성공한다.")
    public void getFollowerList_success() throws Exception {
        // given
        FollowerResponseDto followerResponseDto1 = new FollowerResponseDto(1L);
        FollowerResponseDto followerResponseDto2 = new FollowerResponseDto(2L);
        FollowerResponseDto followerResponseDto3 = new FollowerResponseDto(3L);
        List<FollowerResponseDto> followerResponseDtoList =
                List.of(followerResponseDto1, followerResponseDto2, followerResponseDto3);

        given(followService.getFollowerList(anyString())).willReturn(followerResponseDtoList);

        // when, then
        mockMvc.perform(get("/api/followers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$..['followerId']").exists());
    }

    @Test
    @DisplayName("팔로잉 목록 조회에 성공한다.")
    public void getFollowingList_success() throws Exception {
        // given
        FollowingResponseDto followingResponseDto1 = new FollowingResponseDto(1L);
        FollowingResponseDto followingResponseDto2 = new FollowingResponseDto(2L);
        FollowingResponseDto followingResponseDto3 = new FollowingResponseDto(3L);
        List<FollowingResponseDto> followingResponseDtoList =
                List.of(followingResponseDto1, followingResponseDto2, followingResponseDto3);

        given(followService.getFollowingList(anyString())).willReturn(followingResponseDtoList);

        // when, then
        mockMvc.perform(get("/api/following"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$..['followingId']").exists());
    }
}