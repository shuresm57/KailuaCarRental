package org.example.manager;

import org.example.car.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FamilyManager {

    private Scanner scanner = new Scanner(System.in);

    public FamilyManager(){}

    public FamilyCar createFamilyCar(){
        System.out.println("Enter amount of seats: ");
        int seats = Integer.parseInt(scanner.nextLine());
        System.out.println("Does the car have cruise control? true/false");
        boolean cruiseControl = scanner.nextBoolean();
        return new FamilyCar(
                CarCreationHelper.getBrand(),
                CarCreationHelper.getModel(),
                CarCreationHelper.getRegNo(),
                CarCreationHelper.getOdometer(),
                CarCreationHelper.getEngineSize(),
                seats,
                CarCreationHelper.getHorsepower(),
                cruiseControl,
                CarCreationHelper.getRegDate(),
                CarCreationHelper.getFuelType(),
                CarStatus.AVAILABLE
        );
    }

    public void updateFamilyCar(Car car){
        System.out.println("Choose what you would like to update: " +
                "\n1. Brand" +
                "\n2. Model" +
                "\n3. Registration Number" +
                "\n4. Odometer" +
                "\n5. Engine Size" +
                "\n6. Number of Seats" +
                "\n7. Horsepower" +
                "\n8. Cruise Control" +
                "\n9. Registration Date (yyyy-mm-dd)" +
                "\n10. Fuel Type");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                System.out.print("Enter new Brand: ");
                car.setBrand(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new Model: ");
                car.setModel(scanner.nextLine());
                break;
            case 3:
                System.out.print("Enter new Registration Number: ");
                car.setRegNo(scanner.nextLine());
                break;
            case 4:
                System.out.print("Enter new Odometer reading: ");
                car.setOdoMeter(Integer.parseInt(scanner.nextLine()));
                break;
            case 5:
                System.out.print("Enter new Engine Size: ");
                car.setEngineSize(Integer.parseInt(scanner.nextLine()));
                break;
            case 6:
                System.out.print("Enter new Number of Seats: ");
                car.setSeats(Integer.parseInt(scanner.nextLine()));
                break;
            case 7:
                System.out.print("Enter new Horsepower: ");
                car.setHorsepower(Integer.parseInt(scanner.nextLine()));
                break;
            case 8:
                System.out.print("Enter Cruise Control status (y/n): ");
                car.setCruiseControl(Boolean.parseBoolean(scanner.nextLine()));
                break;
            case 9:
                System.out.print("Enter new Registration Date (dd-mm-yyy): ");
                car.setRegDate(LocalDate.parse(scanner.nextLine()));
                break;
            case 10:
                System.out.print("Enter new Fuel Type: ");
                car.setFuelType(FuelType.valueOf(scanner.nextLine().toUpperCase()));
                break;
            default:
                System.out.println("Invalid choice, please select a valid option.");
                break;
        }

        //String brand, String model, String regNo, int odoMeter, int engineSize, int seats, int horsepower, boolean cruiseControl, LocalDate regDate,FuelType fuelType
    }
}
