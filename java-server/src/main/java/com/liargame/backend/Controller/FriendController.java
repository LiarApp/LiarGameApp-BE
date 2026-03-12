package com.liargame.backend.Controller;

import com.liargame.backend.DTO.FriendDTO.FriendRequest;
import com.liargame.backend.DTO.FriendDTO.FriendResponse;
import com.liargame.backend.Service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/request")
    public ResponseEntity<Void> sendFriendRequest(@RequestBody FriendRequest request) {
        /*
        * 친구 추가를 요청하는 controller입니다.
        */
        friendService.sendFriendRequest(request.getUserId(), request.getFriendId());
        // 204 상태코드 return
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/request/pending")
    public ResponseEntity<List<FriendResponse>> getReceivedRequest(@RequestParam Long userId) {
        /*
        * 받은 친구 요청 목록을 조회하는 controller입니다.
        */
        List<FriendResponse> response = friendService.getReceivedRequest(userId);
        // 200 상태코드와 response return
        return ResponseEntity.ok(response);
    }

    // 보낸 친구 요청 목록 조회
    @GetMapping("/request/sent")
    public ResponseEntity<List<FriendResponse>> getSentRequest(@RequestParam Long userId) {
        /*
        * 보낸 친구 요청 목록을 조회하는 controller입니다.
        */
        List<FriendResponse> response = friendService.getSentRequest(userId);
        return ResponseEntity.ok(response);
    }

    // 친구 추가 요청 수락
    @PatchMapping("/request/{userId}/{friendId}")
    public ResponseEntity<Void> acceptFriendRequest(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.acceptFriendRequest(userId, friendId);
        return ResponseEntity.noContent().build();
    }

    // 친구 추가 요청 거절
    @DeleteMapping("request/{userId}/{friendId}")
    public ResponseEntity<Void> declineFriendRequest(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.declineFriendRequest(userId, friendId);
        return ResponseEntity.noContent().build();
    }

    // 보낸 친구 요청 취소
    @DeleteMapping("/request/{userId}/{friendId}")
    public ResponseEntity<Void> cancelFriendRequest(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.cancelFriendRequest(userId, friendId);
        return ResponseEntity.noContent().build();
    }

    // 친구 삭제
    @DeleteMapping("/{userId}/{friendId}")
    public ResponseEntity<Void> deleteFriendship(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.deleteFriendship(userId, friendId);
        // 반환할 response가 없으므로 204 상태 코드만 return
        return ResponseEntity.noContent().build();
    }

    // 친구 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<FriendResponse>> getFriendList(@RequestParam Long userId) {
        List<FriendResponse> response = friendService.getFriendList(userId);
        return ResponseEntity.ok(response);
    }

    // 닉네임으로 유저 검색
    @GetMapping("/search")
    public ResponseEntity<FriendResponse> searchUserByNickname(@RequestParam String nickname) {
        FriendResponse response = friendService.searchUserByNickname(nickname);
        return ResponseEntity.ok(response);
    }
}
