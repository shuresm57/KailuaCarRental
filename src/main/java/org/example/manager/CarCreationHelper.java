package org.example.manager;
import org.example.car.FuelType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CarCreationHelper {

    private static final Scanner scanner = new Scanner(System.in);

    private static String brand;
    private static String model;
    private static String regNo;
    private static int odometer;
    private static int engineSize;
    private static int horsepower;
    private static LocalDate regDate;
    private static FuelType fuelType;

    public CarCreationHelper() {
    }

    public static void startCreation(){
        setBrand();
        setModel();
        setRegNo();
        setOdometer();
        setEngineSize();
        setHorsepower();
        setRegDate();
        setFuelType();
    }

    public static void setBrand() {
        System.out.println("Enter brand: ");
        brand = scanner.nextLine();
    }

    public static void setModel() {
        System.out.println("Enter model: ");
        model = scanner.nextLine();
    }

    public static void setRegNo() {
        System.out.println("Enter registration number (plate): ");
        regNo = scanner.nextLine();
    }

    public static void setOdometer() {
        System.out.println("Enter odometer reading: ");
        odometer = Integer.parseInt(scanner.nextLine());
    }

    public static void setEngineSize() {
        System.out.println("Enter engine size: ");
        engineSize = Integer.parseInt(scanner.nextLine());
    }

    public static void setHorsepower() {
        System.out.println("Enter horsepower: ");
        horsepower = Integer.parseInt(scanner.nextLine());
    }

    public static void setRegDate() {
        System.out.println("Enter registration date (dd-MM-yyyy): ");
        String dateAsString = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        regDate = LocalDate.parse(dateAsString, formatter);
    }

    public static void setFuelType() {
        System.out.println("Enter fuel type: " + "\n1. Electric\n2. Gasoline \n3. Diesel");
        int fuelChoice = Integer.parseInt(scanner.nextLine());
        switch (fuelChoice) {
            case 1 -> fuelType = FuelType.ELECTRIC;
            case 2 -> fuelType = FuelType.GASOLINE;
            case 3 -> fuelType = FuelType.DIESEL;
            default -> {
                System.out.println("Invalid choice. Defaulting to Gasoline.");
                fuelType = FuelType.GASOLINE;
            }
        }
    }

    public static String getBrand() {
        return brand;
    }

    public static String getModel() {
        return model;
    }

    public static String getRegNo() {
        return regNo;
    }

    public static int getOdometer() {
        return odometer;
    }

    public static int getEngineSize() {
        return engineSize;
    }

    public static int getHorsepower() {
        return horsepower;
    }

    public static LocalDate getRegDate() {
        return regDate;
    }

    public static FuelType getFuelType() {
        return fuelType;
    }
}
