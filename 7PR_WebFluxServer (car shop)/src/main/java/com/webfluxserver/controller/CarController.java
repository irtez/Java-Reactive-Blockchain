package com.webfluxserver.controller;

import com.webfluxserver.exception.CustomException;
import com.webfluxserver.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.webfluxserver.repository.CarRepository;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarRepository carRepository;

    @Autowired
    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Car>> getCarById(@PathVariable Long id) {
        return carRepository.findById(id)
                .map(car -> ResponseEntity.ok(car))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<Car> getAllCars(@RequestParam(name = "maxPrice", required = false) Integer maxPrice) {
        Flux<Car> cars = carRepository.findAll();

        if (maxPrice != null && maxPrice > 0) {
            // Если параметр "maxPrice" указан, применяем фильтрацию
            cars = cars.filter(car -> car.getPrice() <= maxPrice);
        }

        return cars
                .map(this::transformCar) // Применение оператора map для преобразования объектов Car
                .onErrorResume(e -> {
                    // Обработка ошибок
                    return Flux.error(new CustomException("Failed to fetch cars", e));
                })
                .onBackpressureBuffer(); // Работа в формате backpressure
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Car> createCar(@RequestBody Car car) {
        return carRepository.save(car);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Car>> updateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
        return carRepository.findById(id)
                .flatMap(existingCar -> {
                    if (updatedCar.getName() != null) {
                        existingCar.setName(updatedCar.getName());
                    }
                    if (updatedCar.getBrand() != null) {
                        existingCar.setBrand(updatedCar.getBrand());
                    }
                    if (updatedCar.getColor() != null) {
                        existingCar.setColor(updatedCar.getColor());
                    }
                    if (updatedCar.getMileage() != null) {
                        existingCar.setMileage(updatedCar.getMileage());
                    }
                    if (updatedCar.getYear() != null) {
                        existingCar.setYear(updatedCar.getYear());
                    }
                    if (updatedCar.getPrice() != null) {
                        existingCar.setPrice(updatedCar.getPrice());
                    }

                    return carRepository.save(existingCar);

                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCar(@PathVariable Long id) {
        return carRepository.findById(id)
                .flatMap(existingCar ->
                        carRepository.delete(existingCar)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private Car transformCar(Car car) {
        car.setName(car.getName().toUpperCase());
        car.setBrand(car.getBrand().toUpperCase());
        String color = car.getColor().substring(0, 1).toUpperCase() + car.getColor().substring(1);
        car.setColor(color);
        return car;
    }
}
