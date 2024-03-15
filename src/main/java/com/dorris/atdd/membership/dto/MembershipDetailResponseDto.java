package com.dorris.atdd.membership.dto;

import com.dorris.atdd.membership.domain.type.MembershipType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class MembershipDetailResponseDto {
    private final Long id;
    private final MembershipType membershipType;
    private final Long point;
    private final LocalDateTime createdAt;

}
