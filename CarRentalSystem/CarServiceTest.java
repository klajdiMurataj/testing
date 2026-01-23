package org.example.carrental.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.carrental.model.enums.CarCategory;
import org.example.carrental.service.CarService;
import org.example.carrental.storage.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

class CarServiceTest {

    private CarRepository carRepository;
    private CarService carService;

    @BeforeEach
    void setup() {
        carRepository = mock(CarRepository.class);
        carService = new CarService(carRepository);
    }

    @Test
    void testAddCar_valid_noPhoto() {
        when(carRepository.existsByLicensePlate("AA123BB")).thenReturn(false);

        boolean result = carService.addCar(
                "Toyota", "Corolla", 2020,
                "AA123BB", 50,
                CarCategory.SEDAN, 5,
                "Automatic", "Petrol",
                null
        );

        assertTrue(result);
    }

    @Test
    void testAddCar_emptyBrand() {
        assertThrows(IllegalArgumentException.class, () ->
            carService.addCar(
                "", "Corolla", 2020,
                "AA123BB", 50,
                CarCategory.SEDAN, 5,
                "Automatic", "Petrol",
                null
            )
        );
    }

    @Test
    void testAddCar_invalidYear() {
        assertThrows(IllegalArgumentException.class, () ->
            carService.addCar(
                "Toyota", "Corolla", 1800,
                "AA123BB", 50,
                CarCategory.SEDAN, 5,
                "Automatic", "Petrol",
                null
            )
        );
    }

    @Test
    void testAddCar_invalidLicensePlate() {
        assertThrows(IllegalArgumentException.class, () ->
            carService.addCar(
                "Toyota", "Corolla", 2020,
                "123", 50,
                CarCategory.SEDAN, 5,
                "Automatic", "Petrol",
                null
            )
        );
    }

    @Test
    void testAddCar_negativeDailyRate() {
        assertThrows(IllegalArgumentException.class, () ->
            carService.addCar(
                "Toyota", "Corolla", 2020,
                "AA123BB", -10,
                CarCategory.SEDAN, 5,
                "Automatic", "Petrol",
                null
            )
        );
    }

    @Test
    void testAddCar_seatsTooLow() {
        assertThrows(IllegalArgumentException.class, () ->
            carService.addCar(
                "Toyota", "Corolla", 2020,
                "AA123BB", 50,
                CarCategory.SEDAN, 1,
                "Automatic", "Petrol",
                null
            )
        );
    }

    @Test
    void testAddCar_seatsTooHigh() {
        assertThrows(IllegalArgumentException.class, () ->
            carService.addCar(
                "Toyota", "Corolla", 2020,
                "AA123BB", 50,
                CarCategory.SEDAN, 10,
                "Automatic", "Petrol",
                null
            )
        );
    }

    @Test
    void testAddCar_duplicateLicensePlate() {
        when(carRepository.existsByLicensePlate("AA123BB")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
            carService.addCar(
                "Toyota", "Corolla", 2020,
                "AA123BB", 50,
                CarCategory.SEDAN, 5,
                "Automatic", "Petrol",
                null
            )
        );
    }
}
