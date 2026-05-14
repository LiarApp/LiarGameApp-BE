package com.liargame.backend.Repository;

import com.liargame.backend.Entity.CommonAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommonAccountRepository extends JpaRepository<CommonAccount, Long> {
    Optional<CommonAccount> findByPhoneNumber(String phoneNumber);
}
