package com.liargame.backend.Repository;

import com.liargame.backend.Entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
