package com.liargame.backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user.id", nullable = false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "game.id", nullable = false)
    private Game game;

    @Column(name = "is_admin", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isAdmin = false;

    @Column(name = "status")
    private Status status;

    @Column(name = "is_citizen", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isCitizen = false;

    @Column(name = "is_won", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isWon = false;

    @Column(name = "earned_coin DEFAULT 0")
    private Long earnedCoin = 0L;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
