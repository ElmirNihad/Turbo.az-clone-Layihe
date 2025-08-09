package org.example.turboaz.controller;

import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.AuthResponse;
import org.example.turboaz.dto.ConfirmOtpRequest;
import org.example.turboaz.dto.RegisterRequest;
import org.example.turboaz.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/confirm")
    public AuthResponse confirm(@RequestBody ConfirmOtpRequest request) {
        return authService.confirmOtp(request);
    }
}

