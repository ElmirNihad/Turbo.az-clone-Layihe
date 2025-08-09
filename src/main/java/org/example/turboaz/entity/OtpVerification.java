package org.example.turboaz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "OtpVerification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Email")
    private String email;

    @Column(name = "OTP")
    private String otp;

    @Column(name = "ExpireTime")
    private LocalDateTime expiryTime;
}
