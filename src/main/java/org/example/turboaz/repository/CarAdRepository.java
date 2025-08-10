package org.example.turboaz.repository;

import org.example.turboaz.entity.CarAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarAdRepository extends JpaRepository<CarAd,Long> {
    List<CarAd> findByUserId(Long userId);

}
