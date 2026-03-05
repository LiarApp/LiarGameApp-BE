package com.liargame.backend.Repository;

import com.liargame.backend.Entity.ProfileImg;
import com.liargame.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    boolean existsByNickname(String nickname); // 중복 확인

    // 사용자 닉네임, 프로필 이미지를 업데이트합니다.
    @Modifying
    @Query("UPDATE User u " +
            "SET u.nickname = :nickname, u.profileImg = :profileImg " +
            "WHERE u.id =: id"
    )
    int updateUserInfo(
        @Param("id") Long id,
        @Param("nickname") String nickname,
        @Param("profileImg") ProfileImg profileImg
    );
}
