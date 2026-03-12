package com.liargame.backend.Service;

import com.liargame.backend.DTO.FriendDTO.FriendResponse;
import com.liargame.backend.Entity.Friend;
import com.liargame.backend.Entity.FriendStatus;
import com.liargame.backend.Entity.User;
import com.liargame.backend.Repository.FriendRepository;
import com.liargame.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // 친구 추가 요청
    public void sendFriendRequest(Long userId, Long friendId) {
        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));
        User receiver = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + friendId));

        Friend newFriend = new Friend();

        newFriend.setSender(sender);
        newFriend.setReceiver(receiver);
        newFriend.setStatus(FriendStatus.PENDING);

        friendRepository.save(newFriend);
    }

    // 받은 친구 요청 목록 조회
    public List<FriendResponse> getReceivedRequest(Long userId) {
        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));

        List<Friend> friendList = friendRepository.findByReceiverAndStatus(receiver, FriendStatus.PENDING);

        List<FriendResponse> response = new ArrayList<>();

        for (Friend f: friendList) {
            User sender = f.getSender();
            FriendResponse friendResponse = new FriendResponse(
                    sender.getId(),
                    sender.getNickname(),
                    sender.getProfileImg()
            );
            response.add(friendResponse);
        }

        return response;
    }

    // 보낸 친구 요청 목록 조회
    public List<FriendResponse> getSentRequest(Long userId) {
        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));

        List<Friend> friendList = friendRepository.findBySenderAndStatus(sender, FriendStatus.PENDING);
        List<FriendResponse> response = new ArrayList<>();

        for (Friend f: friendList) {
            User receiver = f.getReceiver();
            FriendResponse friendResponse = new FriendResponse(
                    receiver.getId(),
                    receiver.getNickname(),
                    receiver.getProfileImg()
            );
            response.add(friendResponse);
        }

        return response;
    }

    // 친구 추가 요청 수락
    @Transactional
    public void acceptFriendRequest(Long userId, Long friendId) {
        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));

        User sender = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + friendId));

        friendRepository.updateStatus(sender, receiver, FriendStatus.PENDING);
    }

    // 친구 추가 요청 거절
    @Transactional
    public void declineFriendRequest(Long userId, Long friendId) {
        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));

        User sender = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + friendId));

        friendRepository.deleteBySenderAndReceiverAndStatus(sender, receiver, FriendStatus.PENDING);
    }

    // 보낸 친구 요청 취소
    public void cancelFriendRequest(Long userId, Long friendId) {
        User sender = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));

        User receiver = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + friendId));

        friendRepository.deleteBySenderAndReceiverAndStatus(sender, receiver, FriendStatus.PENDING);
    }

    // 친구 삭제
    public void deleteFriendship(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + friendId));

        friendRepository.deleteFriendship(user, friend);
    }

    // 친구 목록 조회
    public List<FriendResponse> getFriendList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));

        List<Friend> friendList = friendRepository.findAllFriends(user);
        List<FriendResponse> response = new ArrayList<>();

        for (Friend f : friendList) {
            User friend = f.getSender().equals(user) ? f.getReceiver() : f.getSender();

            FriendResponse friendResponse = new FriendResponse(
                    friend.getId(),
                    friend.getNickname(),
                    friend.getProfileImg()
            );

            response.add(friendResponse);
        }

        return response;
    }

    // 닉네임으로 유저 검색
    public FriendResponse searchUserByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElse(null);

        if (user == null) return null;

        return new FriendResponse(
                user.getId(),
                user.getNickname(),
                user.getProfileImg()
        );
    }
}
