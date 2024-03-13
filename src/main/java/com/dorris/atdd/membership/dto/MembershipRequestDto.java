package com.dorris.atdd.membership.dto;

import com.dorris.atdd.membership.domain.type.MembershipType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MembershipRequestDto {
    @NotNull
    private final MembershipType membershipType;

    @NotNull
    @Min(0)
    private final Integer point;
}
