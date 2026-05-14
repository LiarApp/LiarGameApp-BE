package com.liargame.backend.Service;

import com.liargame.backend.DTO.BlockDTO.BlockResponse;
import com.liargame.backend.Entity.Block;
import com.liargame.backend.Entity.User;
import com.liargame.backend.Repository.BlockRepository;
import com.liargame.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;
    private final UserRepository userRepository;

    public void blockUser(Long userId, Long blockedUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));
        User blockedUser = userRepository.findById(blockedUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + blockedUserId));

        Block block = new Block();
        block.setUser(user);
        block.setBlockedUser(blockedUser);
        blockRepository.save(block);
    }

    public List<BlockResponse> getBlockedUsers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));

        List<Block> blockList = blockRepository.findByUser(user);
        List<BlockResponse> blockResponseList = new ArrayList<>();

        for (Block b : blockList) {
            User blockedUser = b.getBlockedUser();
            BlockResponse blockResponse = new BlockResponse(
                    blockedUser.getId(),
                    blockedUser.getNickname(),
                    blockedUser.getProfileImg()
            );
            blockResponseList.add(blockResponse);
        }

        return blockResponseList;
    }

    @Transactional
    public void unblockUser(Long userId, Long blockedUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));
        User blockedUser = userRepository.findById(blockedUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + blockedUserId));

        blockRepository.deleteByUserAndBlockedUser(user, blockedUser);
    }
}
