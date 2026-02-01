package com.liargame.backend.Repository;

import com.liargame.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
