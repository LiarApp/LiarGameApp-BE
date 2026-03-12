package com.liargame.backend.Repository;

import com.liargame.backend.Entity.Friend;
import com.liargame.backend.Entity.FriendStatus;
import com.liargame.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    // 나에게 온 친구 요청 목록 조회
    List<Friend> findByReceiverAndStatus(User receiver, FriendStatus status);

    // 내가 보낸 친구 요청 목록 조회
    List<Friend> findBySenderAndStatus(User sender, FriendStatus status);

    @Modifying
    @Query(
            "UPDATE Friend f " +
            "SET f.status = 'ACCEPTED' " +
            "WHERE f.sender = :sender AND f.receiver = :receiver AND status = :status"
    )
    void updateStatus(@Param("sender") User sender, @Param("receiver") User receiver, @Param("status") FriendStatus status);

    void deleteBySenderAndReceiverAndStatus(User sender, User receiver, FriendStatus status);

    @Modifying
    @Query(
            "DELETE FROM Friend f " +
            "WHERE ((f.sender = :user AND f.receiver = :friend) " +
            "OR (f.sender = :friend AND f.receiver = :user))" +
            "AND f.status = 'ACCEPTED'"
    )
    void deleteFriendship(@Param("user") User user, @Param("friend") User friend);

    @Query(
            "SELECT f " +
            "FROM Friend f " +
            "WHERE (f.sender = :user OR f.receiver = :user)" +
            "AND f.status = 'ACCEPTED'"
    )
    List<Friend> findAllFriends(@Param("user") User user);
}
