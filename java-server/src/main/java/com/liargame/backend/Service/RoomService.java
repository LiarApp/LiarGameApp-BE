package com.liargame.backend.Service;

import com.liargame.backend.Entity.Category;
import com.liargame.backend.Entity.Game;
import com.liargame.backend.Entity.Mode;
import com.liargame.backend.Entity.Status;
import com.liargame.backend.Repository.GameRepository;
import com.liargame.backend.Repository.PlayerRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final

    public Boolean createRoom(
        String title,
        Mode mode,
        Integer numOfPeople,
        Integer voteTime,
        Integer answerTime,
        Long categoryId,
        Long userId
    ) {

        // 게임 방 생성
        // 관리자 지정
    }

    public ResponseEntity<Boolean> joinRoom() {
    }

   public ResponseEntity<?> getRooms() {
            return ResponseEntity.ok()
   }

   public ResponseEntity<?> leaveRoom() {

   }
}
