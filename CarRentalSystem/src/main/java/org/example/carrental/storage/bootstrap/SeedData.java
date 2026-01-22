package org.example.carrental.storage.bootstrap;

import org.example.carrental.model.common.Credential;
import org.example.carrental.model.enums.CarCategory;
import org.example.carrental.model.users.Admin;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.storage.repositories.CarRepository;
import org.example.carrental.storage.repositories.UserRepository;
import org.example.carrental.util.HashUtil;
import org.example.carrental.util.IdGenerator;

/**
 * Seeds initial data into the system on first run
 */
public class SeedData {

    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public SeedData(UserRepository userRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    /**
     * Seeds the database if it's empty
     */
    public void seedIfEmpty() {
        seedAdminIfNotExists();
        seedCarsIfEmpty();
    }

    /**
     * Creates the default admin account if it doesn't exist
     */
    private void seedAdminIfNotExists() {
        if (userRepository.findByUsername("admin").isPresent()) {
            return; // Admin already exists
        }

        byte[] salt = HashUtil.generateSalt();
        byte[] hash = HashUtil.hashPassword("admin123", salt);
        Credential credential = new Credential("admin", hash, salt);

        Admin admin = new Admin(
                IdGenerator.generateId("ADMIN"),
                "System",
                "Administrator",
                "admin@carrental.com",
                "1234567890",
                credential,
                "Super Admin"
        );

        userRepository.add(admin);
        System.out.println("✓ Default admin account created (username: admin, password: admin123)");
    }

    /**
     * Seeds sample cars if the car repository is empty
     */
    private void seedCarsIfEmpty() {
        if (carRepository.count() > 0) {
            return; // Cars already exist
        }

        // Economy cars
        carRepository.add(new Car(
                IdGenerator.generateId("CAR"),
                "Toyota",
                "Yaris",
                2023,
                "AA-123-BB",
                35.0,
                true,
                CarCategory.ECONOMY,
                5,
                "Manual",
                "Petrol",
                null
        ));

        carRepository.add(new Car(
                IdGenerator.generateId("CAR"),
                "Hyundai",
                "i20",
                2022,
                "CC-456-DD",
                32.0,
                true,
                CarCategory.ECONOMY,
                5,
                "Manual",
                "Petrol",
                null
        ));

        // Compact cars
        carRepository.add(new Car(
                IdGenerator.generateId("CAR"),
                "Volkswagen",
                "Golf",
                2023,
                "EE-789-FF",
                45.0,
                true,
                CarCategory.COMPACT,
                5,
                "Automatic",
                "Diesel",
                null
        ));

        carRepository.add(new Car(
                IdGenerator.generateId("CAR"),
                "Ford",
                "Focus",
                2022,
                "GG-012-HH",
                42.0,
                true,
                CarCategory.COMPACT,
                5,
                "Manual",
                "Petrol",
                null
        ));

        // Midsize cars
        carRepository.add(new Car(
                IdGenerator.generateId("CAR"),
                "Toyota",
                "Camry",
                2023,
                "II-345-JJ",
                55.0,
                true,
                CarCategory.MIDSIZE,
                5,
                "Automatic",
                "Hybrid",
                null
        ));

        carRepository.add(new Car(
                IdGenerator.generateId("CAR"),
                "Honda",
                "Accord",
                2022,
                "KK-678-LL",
                52.0,
                true,
                CarCategory.MIDSIZE,
                5,
                "Automatic",
                "Petrol",
                null
        ));

        // SUVs
        carRepository.add(new Car(
                IdGenerator.generateId("CAR"),
                "Nissan",
                "Qashqai",
                2023,
                "MM-901-NN",
                65.0,
                true,
                CarCategory.SUV,
                5,
                "Automatic",
                "Diesel",
                null
        ));

        carRepository.add(new Car(
                IdGenerator.generateId("CAR"),
                "Mazda",
                "CX-5",
                2022,
                "OO-234-PP",
                68.0,
                true,
                CarCategory.SUV,
                5,
                "Automatic",
                "Petrol",
                null
        ));

        // Luxury cars
        carRepository.add(new Car(
                IdGenerator.generateId("CAR"),
                "BMW",
                "5 Series",
                2023,
                "QQ-567-RR",
                95.0,
                true,
                CarCategory.LUXURY,
                5,
                "Automatic",
                "Diesel",
                null
        ));

        carRepository.add(new Car(
                IdGenerator.generateId("CAR"),
                "Mercedes-Benz",
                "E-Class",
                2023,
                "SS-890-TT",
                98.0,
                true,
                CarCategory.LUXURY,
                5,
                "Automatic",
                "Petrol",
                null
        ));

        System.out.println("✓ Sample cars seeded (" + carRepository.count() + " cars)");
    }
}