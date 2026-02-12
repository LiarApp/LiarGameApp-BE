package com.liargame.backend.Repository;

import com.liargame.backend.Entity.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import com.liargame.backend.Entity.LoginPath;

import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    // providerId를 조건으로 데이터를 찾는 쿼리 메서드
    Optional<SocialAccount> findByProviderId(String providerId);

    Optional<SocialAccount> findByLoginPathAndProviderId(LoginPath loginPath, String providerId);
}