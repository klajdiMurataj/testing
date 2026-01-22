//THOMAS KROJ
package org.example.carrental.service;

import org.example.carrental.model.vehicles.Car;
import org.example.carrental.storage.repositories.CarRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class CarServiceSearchCarsTest {

    @Test
    void searchCars_null_returnsFindAll() {
        CarRepository repo = mock(CarRepository.class);
        CarService service = new CarService(repo);

        List<Car> expected = List.of();
        when(repo.findAll()).thenReturn(expected);

        assertSame(expected, service.searchCars(null));
        verify(repo, times(1)).findAll();
        verify(repo, never()).search(anyString());
    }

    @Test
    void searchCars_blank_returnsFindAll() {
        CarRepository repo = mock(CarRepository.class);
        CarService service = new CarService(repo);

        List<Car> expected = List.of();
        when(repo.findAll()).thenReturn(expected);

        assertSame(expected, service.searchCars("   "));
        verify(repo, times(1)).findAll();
        verify(repo, never()).search(anyString());
    }

    @Test
    void searchCars_trimsAndSearches() {
        CarRepository repo = mock(CarRepository.class);
        CarService service = new CarService(repo);

        List<Car> expected = List.of();
        when(repo.search("bmW")).thenReturn(expected);

        assertSame(expected, service.searchCars("  bmW "));
        verify(repo, times(1)).search("bmW");
        verify(repo, never()).findAll();
    }
}
