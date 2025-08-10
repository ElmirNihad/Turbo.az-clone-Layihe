package org.example.turboaz.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarAdResponse {
    private Long id;
    private String title;
    private String description;
    private String brand;
    private String model;
    private int year;
    private int mileage;
    private double price;
    private String imageUrl;
    private String createdBy;
}

