package org.example.carrental.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.carrental.model.enums.CarCategory;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.service.CarService;
import org.example.carrental.storage.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class CarServiceUnitTest {

    private CarRepository carRepository;
    private CarService carService;

    @BeforeEach
    void setup() {
        carRepository = mock(CarRepository.class);
        carService = new CarService(carRepository);
    }

    @Test
    void testRemoveCar_existingCar() {
        Car car = mock(Car.class);
        when(car.getPhotoPath()).thenReturn(null);
        when(carRepository.findById("CAR123")).thenReturn(Optional.of(car));
        when(carRepository.remove("CAR123")).thenReturn(true);

        boolean result = carService.removeCar("CAR123");
        assertTrue(result);
        verify(carRepository).remove("CAR123");
    }

    @Test
    void testRemoveCar_notFound() {
        when(carRepository.findById("CAR123")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> carService.removeCar("CAR123"));
    }

    @Test
    void testSetCarAvailability_existingCar() {
        Car car = mock(Car.class);
        when(carRepository.findById("CAR123")).thenReturn(Optional.of(car));

        carService.setCarAvailability("CAR123", false);

        verify(car).setAvailable(false);
        verify(carRepository).update(car);
    }

    @Test
    void testSetCarAvailability_notFound() {
        when(carRepository.findById("CAR123")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> carService.setCarAvailability("CAR123", false));
    }

    @Test
    void testGetAllCars_callsRepository() {
        carService.getAllCars();
        verify(carRepository).findAll();
    }

    @Test
    void testGetAvailableCars_callsRepository() {
        carService.getAvailableCars();
        verify(carRepository).findAvailable();
    }

    @Test
    void testSearchCars_emptyQuery_returnsAll() {
        carService.searchCars("");
        verify(carRepository).findAll();
    }

    @Test
    void testSearchCars_nonEmptyQuery_callsSearch() {
        String query = "Toyota";
        carService.searchCars(query);
        verify(carRepository).search(query);
    }

    @Test
    void testGetCarById_callsRepository() {
        carService.getCarById("CAR123");
        verify(carRepository).findById("CAR123");
    }

    @Test
    void testGetCarsByCategory_callsRepository() {
        CarCategory category = CarCategory.SUV;
        carService.getCarsByCategory(category);
        verify(carRepository).findByCategory(category);
    }
}
