package com.liargame.backend.Controller;

import com.liargame.backend.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    // 방 생성
    @PostMapping("/")
    public ResponseEntity<Boolean> createRoom(@RequestBody ) {
        isCreated = roomService.createRoom(request);
        return ResponseEntity.ok(isCreated);
    }

    // 방 참여
    @PostMapping("/{roomId}/join")
    public ResponseEntity<Boolean> joinRoom(
            @PathVariable Long roomId,
            @RequestBody RoomJoinRequest request
    ) {

    }

    // 방 목록 조회
    @GetMapping("/")
    public ResponseEntity<?> getRooms() {
        return ResponseEntity.ok()
    }

    // 방 퇴장
    @PatchMapping("/{roomId}")
    public ResponseEntity<?> leaveRoom() {

    }
}