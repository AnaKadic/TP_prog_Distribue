package com.example.rental;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private String plateNumber;
    private int price;
    private List<Dates> reservations = new ArrayList<>();

    public Car(String plateNumber, int price) {
        this.plateNumber = plateNumber;
        this.price = price;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public int getPrice() {
        return price;
    }

    public List<Dates> getReservations() {
        return reservations;
    }

    public void addReservation(Dates dates) {
        reservations.add(dates);
    }

    public boolean isAvailable(Dates newDates) {
        for (Dates d : reservations) {
            if (d.getBegin().equals(newDates.getBegin()) && d.getEnd().equals(newDates.getEnd())) {
                return false;
            }
        }
        return true;
    }
}
