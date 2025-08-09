package org.example.turboaz.service;

import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.CarAdResponse;
import org.example.turboaz.dto.CreateCarAdRequest;
import org.example.turboaz.dto.UpdateCarAdRequest;
import org.example.turboaz.entity.CarAd;
import org.example.turboaz.entity.User;
import org.example.turboaz.repository.CarAdRepository;
import org.example.turboaz.repository.UserRepository;
import org.example.turboaz.security.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarAdService {

    private final CarAdRepository carAdRepository;
    private final UserRepository userRepository;

    public void createAd(CreateCarAdRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));

        CarAd ad = new CarAd();
        ad.setTitle(request.getTitle());
        ad.setDescription(request.getDescription());
        ad.setBrand(request.getBrand());
        ad.setModel(request.getModel());
        ad.setYear(request.getYear());
        ad.setMileage(request.getMileage());
        ad.setPrice(request.getPrice());
        ad.setImageUrl(request.getImageUrl());
        ad.setCreatedAt(LocalDateTime.now());
        ad.setUser(user);

        carAdRepository.save(ad);
    }

    public CarAdResponse uploadImage(Long carAdId, MultipartFile file) throws IOException {
        CarAd carAd = carAdRepository.findById(carAdId)
                .orElseThrow(() -> new RuntimeException("Elan tapılmadı"));

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String uploadDir = "uploads/";
        Path path = Paths.get(uploadDir + filename);

        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        carAd.setImageUrl("/" + uploadDir + filename);
        carAdRepository.save(carAd);

        return CarAdResponse.builder()
                .id(carAd.getId())
                .title(carAd.getTitle())
                .description(carAd.getDescription())
                .brand(carAd.getBrand())
                .model(carAd.getModel())
                .year(carAd.getYear())
                .mileage(carAd.getMileage())
                .price(carAd.getPrice())
                .imageUrl(carAd.getImageUrl())
                .build();
    }

    public List<CarAdResponse> getAllAds() {
        return carAdRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CarAdResponse getAdById(Long id) {
        CarAd ad = carAdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Elan tapılmadı"));
        return mapToResponse(ad);
    }

    public void updateAd(Long id, UpdateCarAdRequest request) {
        CarAd ad = carAdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Elan tapılmadı"));

        Long userId = SecurityUtil.getCurrentUserId();
        if (!ad.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Bu elanı yeniləməyə icazəniz yoxdur.");
        }

        ad.setTitle(request.getTitle());
        ad.setDescription(request.getDescription());
        ad.setPrice(request.getPrice());
        carAdRepository.save(ad);
    }

    public void deleteAd(Long id) {
        CarAd ad = carAdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Elan tapılmadı"));

        Long userId = SecurityUtil.getCurrentUserId();
        if (!ad.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Bu elanı silməyə icazəniz yoxdur.");
        }

        carAdRepository.delete(ad);
    }

    private CarAdResponse mapToResponse(CarAd ad) {
        CarAdResponse response = new CarAdResponse();
        response.setId(ad.getId());
        response.setTitle(ad.getTitle());
        response.setDescription(ad.getDescription());
        response.setBrand(ad.getBrand());
        response.setModel(ad.getModel());
        response.setYear(ad.getYear());
        response.setMileage(ad.getMileage());
        response.setPrice(ad.getPrice());
        response.setImageUrl(ad.getImageUrl());
        response.setCreatedBy(ad.getUser().getEmail());
        return response;
    }
    public CarAdResponse convertToDto(CarAd carAd) {
        return CarAdResponse.builder()
                .id(carAd.getId())
                .title(carAd.getTitle())
                .description(carAd.getDescription())
                .brand(carAd.getBrand())
                .model(carAd.getModel())
                .year(carAd.getYear())
                .mileage(carAd.getMileage())
                .price(carAd.getPrice())
                .imageUrl(carAd.getImageUrl())
                .createdBy(carAd.getUser() != null ? carAd.getUser().getEmail() : null)
                .build();
    }

    public List<CarAdResponse> filterAds(Integer minYear, Integer maxYear, String brand, String model,
                                         Double minPrice, Double maxPrice) {
        List<CarAd> ads = carAdRepository.findAll();

        if (minYear != null) {
            ads = ads.stream().filter(ad -> ad.getYear() >= minYear).toList();
        }
        if (maxYear != null) {
            ads = ads.stream().filter(ad -> ad.getYear() <= maxYear).toList();
        }
        if (brand != null) {
            ads = ads.stream().filter(ad -> ad.getBrand().equalsIgnoreCase(brand)).toList();
        }
        if (model != null) {
            ads = ads.stream().filter(ad -> ad.getModel().equalsIgnoreCase(model)).toList();
        }
        if (minPrice != null) {
            ads = ads.stream().filter(ad -> ad.getPrice() >= minPrice).toList();
        }
        if (maxPrice != null) {
            ads = ads.stream().filter(ad -> ad.getPrice() <= maxPrice).toList();
        }

        return ads.stream().map(this::mapToResponse).toList();
    }

}

