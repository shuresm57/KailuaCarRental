package org.example.car;

import java.time.LocalDate;
import java.util.Date;

public class SportCar extends Car {

    public SportCar(String brand, String model, String regNo, int odoMeter, int engineSize, int horsepower, LocalDate regDate, FuelType fuelType,CarStatus carStatus) {
        super(brand, model, regNo, odoMeter, engineSize, 2, horsepower, false, false, false,false,regDate,fuelType,carStatus);
    }
}
