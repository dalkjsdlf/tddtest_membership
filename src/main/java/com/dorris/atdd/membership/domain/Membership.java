package com.dorris.atdd.membership.domain;

import com.dorris.atdd.membership.domain.type.MembershipType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table
@Builder
@AllArgsConstructor
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String userId;

    @Setter
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;

    @Setter
    @Column(nullable = false)
    @ColumnDefault("0")
    private long point;

    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(length = 20)
    private LocalDateTime updatedAt;
}
