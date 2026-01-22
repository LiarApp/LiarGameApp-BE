package com.liargame.backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "profile_image", nullable = true)
    private String profileImg;

    @Column(name = "login_path", nullable = true)
    private String loginPath;
}
