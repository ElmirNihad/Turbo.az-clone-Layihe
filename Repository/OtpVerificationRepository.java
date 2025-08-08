package org.example.turboaz.Repository;

import org.example.turboaz.Entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification ,Long> {
    Optional<OtpVerification> findByEmail(String email);
}
