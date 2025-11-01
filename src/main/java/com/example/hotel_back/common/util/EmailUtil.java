package com.example.hotel_back.common.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender mailSender;
    private final RedisTemplate redisTemplate;

    // 외부에서 사용안함. 여기 내에서 사용
    private void sendMail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // true = enable HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    // 외부에서 호출
    public void sendVerificationEmail(String email) {
        String code = String.valueOf(RandomNumberUtil.get랜덤인증번호());
        redisTemplate.opsForValue().set("verify:" + email, code, 3, TimeUnit.MINUTES);

        this.sendMail(email, "호텔 가입 인증번호", code);
    }

    // 외부에서 호출
    public Boolean verifyCode(String email, String code) {
        String savedCode = (String) redisTemplate.opsForValue().get("verify:" + email);
        Boolean 인증여부 = savedCode != null && savedCode.equals(code);

        if (인증여부) {
            // 인증완료시 회원가입에서 재검증을 위한 값 저장(3분만)
            redisTemplate.opsForValue().set(
                    "verified:" + email,
                    인증여부,
                    3,
                    TimeUnit.MINUTES
            );
        }

        return 인증여부;
    }

    public Boolean isDoneVerifiedEmail(String email) {
        //이메일 검증이 되었는지 확인 메소드
        Boolean 인증완료여부 = (Boolean)redisTemplate.opsForValue().get("verified:" + email);
        return 인증완료여부;
    }
}
