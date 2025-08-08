package org.example.turboaz.Controller;

import lombok.RequiredArgsConstructor;
import org.example.turboaz.DTO.CarAdResponse;
import org.example.turboaz.DTO.CreateCarAdRequest;
import org.example.turboaz.DTO.UpdateCarAdRequest;
import org.example.turboaz.Entity.CarAd;
import org.example.turboaz.Repository.CarAdRepository;
import org.example.turboaz.Service.CarAdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarAdController {

    private final CarAdService carAdService;

    private final CarAdRepository carAdRepository;

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody CreateCarAdRequest request) {
        carAdService.createAd(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/uploadImage/{carAdId}")
    public ResponseEntity<CarAdResponse> uploadImage(@PathVariable Long carAdId,
                                                     @RequestParam("image") MultipartFile file) {
        try {
            CarAdResponse response = carAdService.uploadImage(carAdId, file);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping(path = "/getall")
    public ResponseEntity<List<CarAdResponse>> getAll() {
        return ResponseEntity.ok(carAdService.getAllAds());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<CarAdResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(carAdService.getAdById(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateCarAdRequest request) {
        carAdService.updateAd(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        carAdService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/filter")
    public ResponseEntity<List<CarAdResponse>> filterAds(
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<CarAdResponse> filteredAds = carAdService.filterAds(minYear, maxYear, brand, model, minPrice, maxPrice);
        return ResponseEntity.ok(filteredAds);
    }

}
