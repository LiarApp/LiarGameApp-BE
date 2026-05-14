package com.liargame.backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name="friends")
@Setter
@Getter
@NoArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sender_id", nullable=false)
    private User sender;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="receiver_id", nullable=false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private FriendStatus status;

    @CreationTimestamp
    @Column(name="created_at", updatable=false, nullable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
}