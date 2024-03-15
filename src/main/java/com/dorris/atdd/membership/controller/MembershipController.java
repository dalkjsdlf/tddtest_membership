package com.dorris.atdd.membership.controller;

import com.dorris.atdd.membership.constant.MembershipConstants;
import com.dorris.atdd.membership.dto.MembershipDetailResponseDto;
import com.dorris.atdd.membership.dto.MembershipRequestDto;
import com.dorris.atdd.membership.dto.MembershipResponseDto;
import com.dorris.atdd.membership.service.MembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dorris.atdd.membership.constant.MembershipConstants.USER_ID_HEADER;

@RestController
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @PostMapping("/api/v1/memberships")
    public ResponseEntity<MembershipResponseDto> addMembership(@RequestHeader(USER_ID_HEADER) final String userId,
                                                               @Valid @RequestBody final MembershipRequestDto membershipRequestDto){
        MembershipResponseDto membershipResponseDto = membershipService.addMembership(userId, membershipRequestDto.getMembershipType(), membershipRequestDto.getPoint());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(membershipResponseDto);

    }

    @GetMapping("/api/v1/memberships")
    public ResponseEntity<List<MembershipDetailResponseDto>> getMembershipList(
            @RequestHeader(USER_ID_HEADER) final String userId) {

        return ResponseEntity.ok(membershipService.getMembershipList(userId));
    }
}
