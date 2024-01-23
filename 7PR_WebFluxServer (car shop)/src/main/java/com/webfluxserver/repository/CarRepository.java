package com.webfluxserver.repository;

import com.webfluxserver.model.Car;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends R2dbcRepository<Car, Long> {
}
