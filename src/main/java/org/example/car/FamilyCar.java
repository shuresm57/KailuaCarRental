package org.example.car;

import java.time.LocalDate;

public class FamilyCar extends Car {

    public FamilyCar(String brand, String model, String regNo, int odoMeter, int engineSize, int seats, int horsepower, boolean cruiseControl, LocalDate regDate,FuelType fuelType,CarStatus carStatus) {
        super(brand, model, regNo, odoMeter,engineSize,seats,horsepower, cruiseControl, true, false,false,regDate,fuelType,carStatus);
    }


}
