package org.example.car;

import java.time.LocalDate;
import java.util.Date;

public class LuxuryCar extends Car{
    public LuxuryCar(String brand, String model, String regNo, int odoMeter, int engineSize, int horsepower, int seats, LocalDate regDate, FuelType fuelType,CarStatus carStatus) {
        super(brand, model, regNo, odoMeter,engineSize,seats,horsepower,true,true,true,true, regDate, fuelType,carStatus);
    }
}
