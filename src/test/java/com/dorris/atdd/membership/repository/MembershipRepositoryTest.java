package com.dorris.atdd.membership.repository;

import com.dorris.atdd.membership.domain.Membership;
import com.dorris.atdd.membership.domain.type.MembershipType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@DisplayName("[Repository Test] 멤버쉽 데이터 테스트")
public class MembershipRepositoryTest {

    private MembershipRepository membershipRepository;

    @Autowired
    public MembershipRepositoryTest(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    String userId = "dalkjsdlf";
    int point = 10000;

    @DisplayName("MembershipRepository가 Null인지 검사")
    @Test()
    public void givenNothing_whenCheckNotnull_thenNotnull(){
        // given
        // when
        // then
        assertThat(membershipRepository).isNotNull();
    }

    @DisplayName("특정 사용자의 멤버십 데이터 등록 테스트")
    @Test()
    public void givenMembership_whenRegisterMembership_thenSuccessfullySave(){
        // given
        // 2. 멤버쉽 생성

        final Membership membership = Membership
                .builder()
                .userId(userId)
                .membershipType(MembershipType.NAVER)
                .point(point)
                .build();

        // when
        // 멤버십 Save

        Membership resultMembership = membershipRepository.save(membership);

        // then
        // Save된 결과 Membership객체와 given의 객체가 같은지 비교
        assertThat(resultMembership.getUserId()).isNotNull();
        assertThat(resultMembership.getUserId()).isEqualTo("dalkjsdlf");
        assertThat(resultMembership.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(resultMembership.getPoint()).isEqualTo(10000L);
    }

    @DisplayName("[ Repository Test ] 멤버십이 존재하는지 확인하는테스트 - userId와 멤버십타입으로 검사")
    @Test()
    public void givenMembershipTypeUserId_whenRegisterMembershiAndFind_thenSuccessfullySave(){
        // given
        // 2. 멤버쉽 생성
        MembershipType membershipType = MembershipType.NAVER;

        final Membership membership = Membership
                .builder()
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build();

        // when
        // 멤버십 Save

        membershipRepository.save(membership);
        final Membership resultMembership = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);

        // then
        // Save된 결과 Membership객체와 given의 객체가 같은지 비교
        assertThat(resultMembership.getUserId()).isNotNull();
        assertThat(resultMembership.getUserId()).isEqualTo(userId);
        assertThat(resultMembership.getMembershipType()).isEqualTo(membershipType);
        assertThat(resultMembership.getPoint()).isEqualTo(point);
    }

    @DisplayName("특정 사용자의 멤버십 전체 조회시 0건 조회")
    @Test()
    public void givenUserId_whenFindByUserId_thenResultSize0(){
        // given

        List<Membership> membershipList = membershipRepository.findAllByUserId("12312");

        // when
        assertThat(membershipList.size()).isEqualTo(0);
        // then
    }

    @DisplayName("특정 사용자의 멤버십 전체 조회시 2건 조회")
    @Test()
    public void givenUserIdAndTwoMembership_whenFindByUserId_thenResultSize0(){
        // given

        final Membership naverMembership = Membership
                .builder()
                .userId(userId)
                .membershipType(MembershipType.NAVER)
                .point(point)
                .build();
        final Membership kakaoMembership = Membership
                .builder()
                .userId(userId)
                .membershipType(MembershipType.KAKAO)
                .point(point)
                .build();

        membershipRepository.save(naverMembership);
        membershipRepository.save(kakaoMembership);

        List<Membership> membershipList = membershipRepository.findAllByUserId(userId);

        // when
        assertThat(membershipList.size()).isEqualTo(2);
        // then
    }



}
