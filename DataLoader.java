package org.example.turboaz;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.entity.User;
import org.example.turboaz.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadAdminUser() {
        if (userRepository.findByEmail("admin@turboaz.az").isEmpty()) {
            User admin = User.builder()
                    .email("admin@turboaz.az")
                    .password(passwordEncoder.encode("admin123")) // Şifrə kodlanmalıdır
                    .role("ROLE_ADMIN")
                    .isEnable(true)
                    .name("Admin")
                    .phone("123-456-789")
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin istifadəçi yaradıldı: admin@turboaz.az / admin123");
        }
    }
}
