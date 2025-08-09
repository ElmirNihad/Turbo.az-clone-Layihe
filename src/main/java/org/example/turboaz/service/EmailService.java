package org.example.turboaz.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    public void sendOtp(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Qeydiyyat üçün OTP");
            helper.setText("Təsdiqləmə kodunuz: " + otp);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Email göndərilmədi", e);
        }
    }
}
