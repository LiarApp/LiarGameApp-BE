package com.liargame.backend.Service;

import com.liargame.backend.DTO.LoginResponse;
import com.liargame.backend.Entity.CommonAccount;
import com.liargame.backend.Entity.ProfileImg;
import com.liargame.backend.Entity.User;
import com.liargame.backend.Repository.CommonAccountRepository;
import com.liargame.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonLoginService {
    private final UserRepository userRepository;
    private final CommonAccountRepository commonAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponse register(String phoneNumber, String rawPassword) {
        // 전화번호 중복 확인
        if (commonAccountRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 전화번호입니다.");
        }

        User newUser = new User();
        newUser.setProfileImg(ProfileImg.IMAGE1);
        userRepository.save(newUser);

        CommonAccount account = new CommonAccount();
        account.setPhoneNumber(phoneNumber);
        account.setPassword(passwordEncoder.encode(rawPassword));
        account.setUser(newUser);
        commonAccountRepository.save(account);

        return new LoginResponse(newUser.getId(), false);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(String phoneNumber, String rawPassword) {
        CommonAccount account = commonAccountRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 전화번호입니다."));

        if (!passwordEncoder.matches(rawPassword, account.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new LoginResponse(account.getUser().getId(), true);
    }
}
