package com.webfluxserver;

import com.webfluxserver.model.Car;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.webfluxserver.controller.CarController;
import com.webfluxserver.repository.CarRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CarControllerTest {

    @Test
    public void testGetCarById() {
        // Создание фиктивного автомобиля
        Car car = new Car();
        car.setId(1L);
        car.setName("Cayenne");
        car.setBrand("Porsche");
        car.setColor("Black");
        car.setYear(2020);
        car.setMileage(35000);
        car.setPrice(7500000);

        // Создание мока репозитория
        CarRepository carRepository = Mockito.mock(CarRepository.class);
        when(carRepository.findById(1L)).thenReturn(Mono.just(car));

        // Создание экземпляра контроллера
        CarController carController = new CarController(carRepository);

        // Вызов метода контроллера и проверка результата
        ResponseEntity<Car> response = carController.getCarById(1L).block();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(car, response.getBody());
    }

    @Test
    public void testGetAllCars() {
        // Список фиктивных автомобилей
        Car car1 = new Car();
        car1.setId(1L);
        car1.setName("Cayenne");
        car1.setBrand("Porsche");
        car1.setColor("Black");
        car1.setYear(2020);
        car1.setMileage(35000);
        car1.setPrice(7500000);

        Car car2 = new Car();
        car2.setId(2L);
        car2.setName("Macan");
        car2.setBrand("Porsche");
        car2.setColor("Silver");
        car2.setYear(2018);
        car2.setMileage(120000);
        car2.setPrice(4500000);

        // Мок репозитория
        CarRepository carRepository = Mockito.mock(CarRepository.class);
        when(carRepository.findAll()).thenReturn(Flux.just(car1, car2));

        // Экземпляр контроллера
        CarController carController = new CarController(carRepository);

        // Вызов метода контроллера и проверка результата
        Flux<Car> response = carController.getAllCars(null);
        assertEquals(2, response.collectList().block().size());
    }

    @Test
    public void testCreateCar() {
        // Создание фиктивного автомобиля
        Car car = new Car();
        car.setId(1L);
        car.setName("Cayenne");
        car.setBrand("Porsche");
        car.setColor("Black");
        car.setYear(2020);
        car.setMileage(35000);
        car.setPrice(7500000);

        // Мок репозитория
        CarRepository carRepository = Mockito.mock(CarRepository.class);
        when(carRepository.save(car)).thenReturn(Mono.just(car));

        // Экземпляр контроллера
        CarController carController = new CarController(carRepository);

        // Вызов метода контроллера и проверка результата
        Mono<Car> response = carController.createCar(car);
        assertEquals(car, response.block());
    }

    @Test
    public void testUpdateCar() {
        // Создание фиктивного автомобиля
        Car existingCar = new Car();
        existingCar.setId(1L);
        existingCar.setName("Cayenne");
        existingCar.setBrand("Porsche");
        existingCar.setColor("Black");
        existingCar.setYear(2020);
        existingCar.setMileage(35000);
        existingCar.setPrice(7500000);

        // Создание фиктивного обновленного автомобиля
        Car updatedCar = new Car();
        updatedCar.setId(1L);
        updatedCar.setName("Macan");
        updatedCar.setBrand("Porsche");
        updatedCar.setColor("Silver");
        updatedCar.setYear(2018);
        updatedCar.setMileage(120000);
        updatedCar.setPrice(4500000);

        // Мок репозитория
        CarRepository carRepository = Mockito.mock(CarRepository.class);
        when(carRepository.findById(1L)).thenReturn(Mono.just(existingCar));
        when(carRepository.save(existingCar)).thenReturn(Mono.just(updatedCar));

        // Экземпляр контроллера
        CarController carController = new CarController(carRepository);

        // Вызов метода контроллера и проверка результата
        ResponseEntity<Car> response = carController.updateCar(1L, updatedCar).block();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCar, response.getBody());
    }

    @Test
    public void testDeleteCar() {
        // Создание фиктивного автомобиля
        Car car = new Car();
        car.setId(1L);
        car.setName("Cayenne");
        car.setBrand("Porsche");
        car.setColor("Black");
        car.setYear(2020);
        car.setMileage(35000);
        car.setPrice(7500000);

        // Мок репозитория
        CarRepository carRepository = Mockito.mock(CarRepository.class);
        when(carRepository.findById(1L)).thenReturn(Mono.just(car));
        when(carRepository.delete(car)).thenReturn(Mono.empty());

        // Экземпляр контроллера
        CarController carController = new CarController(carRepository);

        // Вызов метода контроллера и проверка результата
        ResponseEntity<Void> response = carController.deleteCar(1L).block();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}

