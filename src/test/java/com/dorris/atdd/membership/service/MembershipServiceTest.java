package com.dorris.atdd.membership.service;


import com.dorris.atdd.membership.domain.Membership;
import com.dorris.atdd.membership.domain.type.MembershipType;
import com.dorris.atdd.membership.dto.MembershipResponseDto;
import com.dorris.atdd.membership.exception.MembershipErrorResult;
import com.dorris.atdd.membership.exception.MembershipException;
import com.dorris.atdd.membership.repository.MembershipRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/***
 * 서비스테스트
 * 1. 서비스 객체 null 검사 테스트
 * 2. 멤버쉽 생성 서비스
 */
@DisplayName("[Service Test] 멤버쉽 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {

    private Logger logger = LoggerFactory.getLogger(MembershipServiceTest.class);
    private final String userId = "dalkjsdlf";
    private final MembershipType membershipType = MembershipType.NAVER;
    private final Integer point = 10000;

    @InjectMocks
    private MembershipService target;

    @Mock
    private MembershipRepository membershipRepository;

    @DisplayName("MembershipService가 Notnull 검사")
    @Test()
    public void givenNothing_when_thenIsNotNull(){
        // given

        // when

        // then
        assertThat(target).isNotNull();
    }

    /***
     * @Test Name : 멤버십 등록된 서비스
     * @Desc : 이미 등록된 멤버십이 없으면 멤버십을 등록한다.
     * @Given : 멤버십 객체
     * @When : 멤버십 객체 중체크 한 후 에러발생 !!
     * @Then : 메시지 중복 예외 발생
     * @Exception : 중복된 멤버십이 있다.
     */
    @DisplayName("[ Service Test | Throw ] 특정 사용자의 멤버십 생성 서비스시 멤버십 중복 테스트")
    @Test()
    public void givenMembership_whenRegisterMembership_thenDupError(){
        // given
        // Membership
        // Resository Mock
        Membership membership = Membership.builder()
                .id(1L)
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build();

        doReturn(membership).when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);

        // when
        final MembershipException result = Assertions.assertThrows(MembershipException.class, ()->target.addMembership(userId, membershipType, point));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);
    }


    @DisplayName("[ Service Test ] 특정 사용자의 멤버십 생성 서비스 멤버십 등록 테스트")
    @Test()
    public void givenMembership_whenRegisterMembership_thenSuccessfullyRegister(){
        // given
        // Membership
        // Resository Mock

        Membership membership = Membership.builder()
                .id(1L)
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build();

        //중복검사
        //null이니깐 이미 등록된 값 없음(중복 없음)
        doReturn(null).when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);

        //저장값의 결과 membership
        doReturn(membership).when(membershipRepository).save(any(Membership.class));

        // when
        final MembershipResponseDto result = target.addMembership(userId, membershipType, point);

        //logger.debug("target test 결과 userid : [{}] membershiptype [{}]",result.getUserId(), result.getMembershipType());
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getMembershipType()).isEqualTo(membershipType);

        //verify
        verify(membershipRepository, times(1)).findByUserIdAndMembershipType(userId, membershipType);
        verify(membershipRepository, times(1)).save(any(Membership.class));
    }
}
