package com.example.rentalService.web;

import com.example.rentalService.data.Car;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RentalWebService {

    Logger logger = LoggerFactory.getLogger(RentalWebService.class);
    List<Car> cars = new ArrayList<Car>();

    public RentalWebService(){
        cars.add(new Car("AA11BB", "Ferrari", 1000));
    }

    @GetMapping("/bonjour")
    public String disBonjour() {
        return "Bonjour !";
    }

    @PutMapping(value = "/cars/{plateNumber}")
    public void rent(
            @PathVariable("plateNumber") String plateNumber,
            @RequestParam(value="rent", required = true)boolean rent,
            @RequestBody Dates dates){

        logger.info("PlateNumber:" + plateNumber);
        logger.info("Rent:" + rent);
        logger.info("Dates:" + dates);
    }

    @GetMapping("/cars")
    public List<Car> getAllCars(){
        return cars;
    }

    @GetMapping("/cars/{plateNumber}")
    public Car getACar(@PathVariable("plateNumber") String plateNumber) throws CarNotFoundException {
        return cars.stream().filter(car -> car.getPlateNumber().equals(plateNumber)).findFirst().orElseThrow(() -> new CarNotFoundException(plateNumber));
    }



}
