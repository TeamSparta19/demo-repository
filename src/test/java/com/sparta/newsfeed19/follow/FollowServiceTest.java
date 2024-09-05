package com.sparta.newsfeed19.follow;

import com.sparta.newsfeed19.data.FollowMockData;
import com.sparta.newsfeed19.follow.dto.FollowRequestDto;
import com.sparta.newsfeed19.global.exception.ApiException;
import com.sparta.newsfeed19.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.sparta.newsfeed19.global.exception.ResponseCode.NOT_FOUND_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    FollowRepository followRepository;

    @InjectMocks
    FollowService followService;

    @Test
    public void 본인_이메일이_잘못_되어서_팔로우에_실패한다() {
        // given
        FollowRequestDto followRequestDto = FollowMockData.followRequestDto(1L);
        given(userRepository.findActiveUserByEmail(anyString())).willThrow(new ApiException(NOT_FOUND_USER));

        // when
        Throwable throwable = assertThrows(ApiException.class, () -> followService.follow("tttt", followRequestDto));

        // then
        assertEquals(NOT_FOUND_USER.getMessage(), throwable.getMessage());
    }

//    @Test
//    public void 자기_자신을_팔로우_할_수_없기_때문에_실패한다() {
//       // given
//       FollowRequestDto followRequestDto = FollowMockData.followRequestDto(1L);
//       User user = new User();
//       given(userRepository.findByEmail(anyString())).willReturn(Optional.of());
//
//       // when
//
//
//       // then
//    }
}