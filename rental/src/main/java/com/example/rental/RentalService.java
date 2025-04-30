package com.example.rental;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RentalService {

    private List<Car> cars = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(RentalService.class);

    public RentalService() {
        cars.add(new Car("Ferrari", 1000));
        cars.add(new Car("Porsche", 2000));
        cars.add(new Car("Peugeot", 500));
    }

    @GetMapping("/cars")
    public List<Car> getCars() {
        return cars;
    }

    @PutMapping("/cars/{plateNumber}")
    public ResponseEntity<String> rent(
            @PathVariable String plateNumber,
            @RequestParam boolean rent,
            @RequestBody Dates dates) {

        for (Car car : cars) {
            if (car.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                if (rent) {
                    if (car.isAvailable(dates)) {
                        car.addReservation(dates);
                        return ResponseEntity.ok("Réservation confirmée.");
                    } else {
                        return ResponseEntity.status(409).body("Voiture déjà réservée à ces dates.");
                    }
                } else {
                    return ResponseEntity.ok("Aucune action effectuée.");
                }
            }
        }

        return ResponseEntity.status(404).body("Voiture non trouvée.");
    }
    
    @GetMapping("/cars/{plateNumber}/reservations")
    public List<Dates> getReservations(@PathVariable String plateNumber) {
        for (Car car : cars) {
            if (car.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                return car.getReservations();
            }
        }
        return new ArrayList<>();
    }

}
