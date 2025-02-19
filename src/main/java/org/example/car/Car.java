package org.example.car;

import java.time.LocalDate;

//Abstract class, da en bil kun kan være family, luxury eller sport. Derfor skal man ikke kunne instantiere Car klassen
// Subklasserne arver egenskaber og metoder fra denne klasse.
public abstract class Car {

    protected String brand;
    protected String model;
    protected String regNo;

    protected int odoMeter;
    protected int engineSize;
    protected int seats;
    protected int horsepower;

    protected boolean automaticGear;
    protected boolean airCondition;
    protected boolean cruiseControl;
    protected boolean leatherSeats;

    protected LocalDate regDate;

    protected FuelType fuelType;

    protected CarStatus carStatus;

    public Car(){}

    public Car(String brand, String model, String regNo,
               int odoMeter, int engineSize, int seats, int horsepower,
               boolean automaticGear, boolean airCondition, boolean cruiseControl, boolean leatherSeats,
               LocalDate regDate, FuelType fuelType, CarStatus carStatus) {
        this.brand = brand;
        this.model = model;
        this.regNo = regNo;
        this.odoMeter = odoMeter;
        this.engineSize = engineSize;
        this.seats = seats;
        this.horsepower = horsepower;
        this.automaticGear = automaticGear;
        this.airCondition = airCondition;
        this.cruiseControl = cruiseControl;
        this.leatherSeats = leatherSeats;
        this.regDate = regDate;
        this.fuelType = fuelType;
        this.carStatus = carStatus;
    }

    /*I dette tilfælde, ville det være ideelt at bruge et bibliotek som Lombok, som indeholder annoteringer
    * som @Getter og @Setter
    * Til at reducere boilerplate koden og forbedre læsbarheden*/

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo){
        this.regNo = regNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getOdometer() {
        return odoMeter;
    }

    public void setOdoMeter(int odoMeter) {
        this.odoMeter = odoMeter;
    }

    public int getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(int engineSize) {
        this.engineSize = engineSize;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public boolean isAutomaticGear() {
        return automaticGear;
    }

    public boolean isAirCondition() {
        return airCondition;
    }

    public boolean isCruiseControl() {
        return cruiseControl;
    }

    public void setCruiseControl(boolean cruiseControl) {
        this.cruiseControl = cruiseControl;
    }

    public boolean isLeatherSeats() {
        return leatherSeats;
    }

    public int getRegMonth() {
        return regDate.getMonthValue();
    }

    public int getRegYear() {
        return regDate.getYear();
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public String getFuelType() {
        return String.valueOf(fuelType);
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getCarStatus() {
        return String.valueOf(carStatus);
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    @Override
    public String toString() {
        return String.format("""
        ----------------------------
        Car Details
        ----------------------------
        Brand              : %s
        Model              : %s
        Registration No.   : %s
        Odometer           : %d km
        Engine Size        : %d L
        Seats              : %d
        Horsepower         : %d HP
        Automatic Gear     : %s
        Air Condition      : %s
        Cruise Control     : %s
        Leather Seats      : %s
        Registration Date  : %s
        Fuel Type          : %s
        Car Status         : %s
        ----------------------------
        """,
                brand,
                model,
                regNo,
                odoMeter,
                engineSize,
                seats,
                horsepower,
                (automaticGear ? "Yes" : "No"),
                (airCondition ? "Yes" : "No"),
                (cruiseControl ? "Yes" : "No"),
                (leatherSeats ? "Yes" : "No"),
                regDate,
                fuelType,
                carStatus);
    }
}
