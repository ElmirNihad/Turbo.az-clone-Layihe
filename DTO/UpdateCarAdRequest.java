package org.example.turboaz.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateCarAdRequest {
    private String title;
    private String description;
    private double price;
}

