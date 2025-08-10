package org.example.turboaz.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.entity.User;
import org.example.turboaz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.admin.email}")
    private String email;
    @Value("${application.admin.password}")
    private String password;
    @Value("${application.admin.username}")
    private String username;


    @PostConstruct
    public void loadAdminUser() {
        if (userRepository.findByEmail(email).isEmpty()) {
            User admin = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password)) // Şifrə kodlanmalıdır
                    .role("ROLE_ADMIN")
                    .isEnable(true)
                    .name(username)
                    .phone("123-456-789")
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin istifadəçi yaradıldı: admin@turboaz.az / admin123");
        }
    }
}
