package com.liargame.backend.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nickname", nullable=false, unique=true)
    private String nickname;

    @Column(name="profile_image", nullable=true)
    private String profileImg;

    @Enumerated(EnumType.STRING)
    @Column(name="login_path", nullable=true)
    private LoginPath loginPath;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    // 양방향 연관 관계 설정
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<SocialAccount> socialAccounts = new ArrayList<>();
}