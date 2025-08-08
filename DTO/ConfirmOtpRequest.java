package org.example.turboaz.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmOtpRequest {
    private String email;
    private String otp;
}
