package com.liargame.backend.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class SmsService {
    @Value("${twilio.account-sid}") private String accountSid;
    @Value("${twilio.auth-token}") private String authToken;
    @Value("${twilio.from-number}") private String fromNumber;

    private final RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public void sendVerificationCode(String phoneNumber) {
        String code = "123456"; // 고정값
        redisTemplate.opsForValue().set("sms:" + phoneNumber, code, 3, TimeUnit.MINUTES);
        System.out.println("[개발용 인증번호] " + phoneNumber + " : " + code);
        /*
        String code = String.format("%06d", new Random().nextInt(1000000));

        // redis에 저장
        redisTemplate.opsForValue().set("sms:" + phoneNumber, code, 3, TimeUnit.MINUTES);

        // twilio sms 발송
        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(fromNumber),
                "[라이어게임] 인증번호 [" + code + "]를 입력해주세요."
        ).create();

        */
    }

    public boolean verifyCode(String phoneNumber, String inputCode) {
        String savedCode = redisTemplate.opsForValue().get("sms:" + phoneNumber);

        if (savedCode == null) throw new IllegalArgumentException("인증번호가 만료되었습니다.");
        if (!savedCode.equals(inputCode)) throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");

        redisTemplate.delete("sms:" + phoneNumber);

        // 인증 10분 유효
        redisTemplate.opsForValue().set("verified:" + phoneNumber, "true", 10, TimeUnit.MINUTES);
        return true;
    }

    public void checkVerified(String phoneNumber) {
        String verified = redisTemplate.opsForValue().get("verified:" + phoneNumber);
        if (!"true".equals(verified)) {
            throw new IllegalArgumentException("전화번호 인증이 필요합니다.");

        }
    }
}