package com.dorris.atdd.membership.controller;

import com.dorris.atdd.membership.domain.Membership;
import com.dorris.atdd.membership.domain.type.MembershipType;
import com.dorris.atdd.membership.dto.MembershipRequestDto;
import com.dorris.atdd.membership.dto.MembershipResponseDto;
import com.dorris.atdd.membership.exception.GlobalExceptionHandler;
import com.dorris.atdd.membership.exception.MembershipErrorResult;
import com.dorris.atdd.membership.exception.MembershipException;
import com.dorris.atdd.membership.service.MembershipService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static com.dorris.atdd.membership.constant.MembershipConstants.USER_ID_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("[Contorller Test] 멤버쉽 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class MembershipControllerTest {
    @InjectMocks
    private MembershipController target;

    @Mock
    private MembershipService membershipService;

    private MockMvc mockMvc;

    private Gson gson;

    @DisplayName("[Controller test] Controller와 MockMvc가 Null이 아님")
    @Test()
    public void given_when_thenCheckNull(){
        // given
        // when
        // then
        assertThat(target).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @DisplayName("[Controller test] Service가 Null이 아님")
    @Test()
    public void given_when_thenServiceCheckNull(){
        // given
        // when
        // then
        assertThat(membershipService).isNotNull();
    }

    @BeforeEach
    public void init(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @DisplayName("[Controller Test] 실패케이스 - 사용자실별값이 헤더에 없음")
    @Test()
    public void given_whenPostUserHeaderNull_thenBadRequest() throws Exception {
        // given
        final String url = "/api/v1/memberships";

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(gson.toJson(getMembershipRequestDto(MembershipType.NAVER, 10000)))
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println(" >>>>>>  "+status().isBadRequest());
        // then
        resultActions.andExpect(status().isBadRequest());
    }
    @DisplayName("[Controller Test] 실패케이스 - point가 null 일때 실패")
    @Test()
    public void given_whenPostPointIsNull_thenBadRequest() throws Exception {
        // given
        final String url = "/api/v1/memberships";

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .header(USER_ID_HEADER,"12345")
                .content(gson.toJson(getMembershipRequestDto(MembershipType.NAVER, null)))
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println(" >>>>>>  "+status().isBadRequest());

        // then
        resultActions.andExpect(status().isBadRequest());
    }
    @DisplayName("[Controller Test] 실패케이스 - point가 음수일때 실패")
    @Test()
    public void given_whenPostPointIsNegative_thenBadRequest() throws Exception {
        // given
        final String url = "/api/v1/memberships";

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .header(USER_ID_HEADER,"12345")
                .content(gson.toJson(getMembershipRequestDto(MembershipType.NAVER, -1)))
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println(" >>>>>>  "+status().isBadRequest());

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @DisplayName("[Controller Test] 실패케이스 - MembershipType이 null 일때 실패")
    @Test()
    public void given_whenPostMembershipTypeIsNull_thenBadRequest() throws Exception {
        // given
        final String url = "/api/v1/memberships";

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .header(USER_ID_HEADER,"12345")
                .content(gson.toJson(getMembershipRequestDto(null, 10000)))
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println(" >>>>>>  "+status().isBadRequest());

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @DisplayName("[Controller Test] 실패케이스 - MembershipService에서 Exception 발생시")
    @Test()
    public void given_whenPostExceptionFromService_thenBadRequest() throws Exception {
        // given
        final String url = "/api/v1/memberships";

        doThrow(new MembershipException(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER))
                .when(membershipService)
                .addMembership("12345",MembershipType.NAVER,10000);
        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .header(USER_ID_HEADER,"12345")
                .content(gson.toJson(getMembershipRequestDto(MembershipType.NAVER, 10000)))
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println(" >>>>>>  "+status().isBadRequest());

        //verify(membershipService, times(1)).addMembership("12345",MembershipType.NAVER,10000);
        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @DisplayName("[Controller Test] 성공케이스 -  Membership 등록 성공")
    @Test()
    public void givenMembership_whenPostRegister_thenCreatedRequest() throws Exception {
        // given
        final String url = "/api/v1/memberships";

        final MembershipResponseDto membershipResponseDto = MembershipResponseDto
                .builder()
                .id(1L)
                .membershipType(MembershipType.NAVER)
                .build();

        doReturn(membershipResponseDto)
                .when(membershipService)
                .addMembership("12345",MembershipType.NAVER,10000);
        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .header(USER_ID_HEADER,"12345")
                .content(gson.toJson(getMembershipRequestDto(MembershipType.NAVER, 10000)))
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println(" >>>>>>  "+status().isBadRequest());

        //verify(membershipService, times(1)).addMembership("12345",MembershipType.NAVER,10000);
        // then
        resultActions.andExpect(status().isCreated());

        final MembershipResponseDto response = gson.fromJson(resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8),MembershipResponseDto.class);

        assertThat(response.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(response.getId()).isNotNull();

    }

    private MembershipRequestDto getMembershipRequestDto(final MembershipType membershipType, final Integer point){
        return MembershipRequestDto.builder()
                .membershipType(membershipType)
                .point(point)
                .build();
    }
}
