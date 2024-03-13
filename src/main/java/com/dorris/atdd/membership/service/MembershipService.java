package com.dorris.atdd.membership.service;

import com.dorris.atdd.membership.domain.Membership;
import com.dorris.atdd.membership.domain.type.MembershipType;
import com.dorris.atdd.membership.dto.MembershipResponseDto;
import com.dorris.atdd.membership.exception.MembershipErrorResult;
import com.dorris.atdd.membership.exception.MembershipException;
import com.dorris.atdd.membership.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}
