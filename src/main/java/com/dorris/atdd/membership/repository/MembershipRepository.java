package com.dorris.atdd.membership.repository;

import com.dorris.atdd.membership.domain.Membership;
import com.dorris.atdd.membership.domain.type.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Membership findByUserIdAndMembershipType(final String userId, final MembershipType membershipType);

    List<Membership> findAllByUserId(final String userId);
}
