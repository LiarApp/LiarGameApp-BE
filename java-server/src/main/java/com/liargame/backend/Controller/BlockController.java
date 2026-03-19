package com.liargame.backend.Controller;

import com.liargame.backend.DTO.BlockDTO.BlockRequest;
import com.liargame.backend.DTO.BlockDTO.BlockResponse;
import com.liargame.backend.Service.BlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/block")
@RequiredArgsConstructor
public class BlockController {
    private final BlockService blockService;

    @PostMapping("/")
    public ResponseEntity<Void> blockUser(@RequestBody BlockRequest request) {
        blockService.blockUser(request.getUserId(), request.getBlockedUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<BlockResponse>> getBlockedUsers(@RequestParam Long userId) {
        List<BlockResponse> blockedUsers = blockService.getBlockedUsers(userId);
        return ResponseEntity.ok(blockedUsers);
    }

    @DeleteMapping("/{userId}/{blockedUserId}")
    public ResponseEntity<Void> unblockUser(@PathVariable Long userId, @PathVariable Long blockedUserId) {
        blockService.unblockUser(userId, blockedUserId);
        return ResponseEntity.noContent().build();
    }
}
