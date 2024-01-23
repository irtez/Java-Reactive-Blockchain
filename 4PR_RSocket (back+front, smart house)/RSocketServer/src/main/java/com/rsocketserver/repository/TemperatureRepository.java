package com.rsocketserver.repository;

import com.rsocketserver.model.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Long> {
    Temperature findTemperatureById(Long id);
    @Modifying
    @Transactional
    @Query("UPDATE Temperature e SET e.temp_value = :newTemp WHERE e.id = :id")
    void updateTemp(@Param("id") Long id, @Param("newTemp") Float newTemp);
}
