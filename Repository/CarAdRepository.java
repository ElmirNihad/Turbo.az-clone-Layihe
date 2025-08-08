package org.example.turboaz.Repository;

import org.example.turboaz.Entity.CarAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarAdRepository extends JpaRepository<CarAd,Long> {
    List<CarAd> findByUserId(Long userId);

}
