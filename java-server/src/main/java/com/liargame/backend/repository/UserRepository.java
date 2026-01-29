package com.liargame.backend.repository;

import com.liargame.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // email로 찾기

    Optional<User> findByNickname(String nickname);
    boolean existsByNickname(String nickname); // 중복 확인
}
