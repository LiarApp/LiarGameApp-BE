package com.liargame.backend.Repository;

import com.liargame.backend.Entity.Block;
import com.liargame.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {
    List<Block> findByUser(User user);
    void deleteByUserAndBlockedUser(User user, User blockedUser);
}
