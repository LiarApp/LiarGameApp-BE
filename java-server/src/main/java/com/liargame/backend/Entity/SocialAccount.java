package com.liargame.backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="social_accounts")
@Setter
@Getter
@NoArgsConstructor
public class SocialAccount {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    // 소셜 서비스에서 제공하는 고유 id
    @Column(name="provider_id", nullable=false, unique=true)
    private String providerId;

    // 사용자 테이블과의 연관 관계
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
