package org.example.turboaz.controller;

import lombok.RequiredArgsConstructor;
import org.example.turboaz.service.AuthService;
import org.example.turboaz.service.CarAdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CarAdService carAdService;
    private final AuthService authService;

    @DeleteMapping("/delete/car/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
        carAdService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }


}
