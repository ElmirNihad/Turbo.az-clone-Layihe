package org.example.turboaz.Service;

import lombok.RequiredArgsConstructor;
import org.example.turboaz.DTO.AuthResponse;
import org.example.turboaz.DTO.ConfirmOtpRequest;
import org.example.turboaz.DTO.RegisterRequest;
import org.example.turboaz.Entity.OtpVerification;
import org.example.turboaz.Entity.User;
import org.example.turboaz.Repository.OtpVerificationRepository;
import org.example.turboaz.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OtpVerificationRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email artıq qeydiyyatdan keçib.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role("ROLE_USER")
                .isEnable(false)
                .build();
        userRepository.save(user);

        String otp = generateOtp();

        OtpVerification otpVerification = OtpVerification.builder()
                .email(user.getEmail())
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();

        otpRepository.save(otpVerification);
        emailService.sendOtp(user.getEmail(), otp);

        return new AuthResponse("OTP emailə göndərildi. Lütfən təsdiqləyin.");
    }

    public AuthResponse confirmOtp(ConfirmOtpRequest request) {
        OtpVerification otp = otpRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP tapılmadı"));

        if (!otp.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("OTP yanlışdır.");
        }

        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP müddəti bitib.");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı."));

        user.setEnable(true);
        userRepository.save(user);

        otpRepository.delete(otp); // OTP istifadə edildikdən sonra silinsin

        return new AuthResponse("Hesab aktivləşdirildi. İndi login edə bilərsiniz.");
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000); // 6 rəqəmli
    }


}

