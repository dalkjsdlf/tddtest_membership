package com.dorris.atdd.membership.service;

import com.dorris.atdd.membership.domain.Membership;
import com.dorris.atdd.membership.domain.type.MembershipType;
import com.dorris.atdd.membership.dto.MembershipDetailResponseDto;
import com.dorris.atdd.membership.dto.MembershipResponseDto;
import com.dorris.atdd.membership.exception.MembershipErrorResult;
import com.dorris.atdd.membership.exception.MembershipException;
import com.dorris.atdd.membership.repository.MembershipRepository;
import com.fasterxml.classmate.AnnotationOverrides;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipResponseDto addMembership(final String userId, final MembershipType membershipType, final Integer point){

        Membership result = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);

        if (result != null) {
            throw new MembershipException(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);
        }

        Membership membership = Membership
                .builder()
                .id(1L)
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build();

        Membership savedResult = membershipRepository.save(membership);

        return MembershipResponseDto.builder()
                .id(savedResult.getId())
                .membershipType(savedResult.getMembershipType())
                .build();
    }


    public List<MembershipDetailResponseDto> getMembershipList(final String userId) {
        final List<Membership> membershipList = membershipRepository.findAllByUserId(userId);


        return membershipList.stream()
                .map(v -> MembershipDetailResponseDto.builder()
                        .id(v.getId())
                        .membershipType(v.getMembershipType())
                        .point(v.getPoint())
                        .createdAt(v.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
