package com.dorris.atdd.membership.dto;

import com.dorris.atdd.membership.domain.type.MembershipType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MembershipResponseDto {
    private final Long id;
    private final MembershipType membershipType;
}
