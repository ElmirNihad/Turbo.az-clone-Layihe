package org.example.turboaz.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCarAdRequest {
    private String title;
    private String description;
    private String brand;
    private String model;
    private int year;
    private int mileage;
    private double price;
    private String imageUrl;
}

