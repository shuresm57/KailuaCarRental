package org.example.manager;

import org.example.car.*;
import org.example.util.CarHelper;

import java.time.LocalDate;
import java.util.Scanner;

public class LuxuryManager {

    private Scanner scanner = new Scanner(System.in);

    public LuxuryManager(){}

    public LuxuryCar createLuxuryCar(){
        System.out.println("Enter amount of seats: ");
            int seats = Integer.parseInt(scanner.nextLine());
            return new LuxuryCar(
                    CarHelper.getBrand(),
                    CarHelper.getModel(),
                    CarHelper.getRegNo(),
                    CarHelper.getOdometer(),
                    CarHelper.getEngineSize(),
                    CarHelper.getHorsepower(),
                    seats,
                    CarHelper.getRegDate(),
                    CarHelper.getFuelType(),
                    CarStatus.AVAILABLE
            );
    }

    public void updateLuxuryCar(Car car) {
        System.out.println("Choose what you would like to update: " +
                "\n1. Brand" +
                "\n2. Model" +
                "\n3. Registration Number" +
                "\n4. Odometer" +
                "\n5. Engine Size" +
                "\n6. Number of Seats" +
                "\n7. Horsepower" +
                "\n8. Registration Date (yyyy-mm-dd)" +
                "\n9. Fuel Type");

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
                car.setOdometer(Integer.parseInt(scanner.nextLine()));
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
                System.out.print("Enter new Registration Date (dd-mm-yyy): ");
                car.setRegDate(LocalDate.parse(scanner.nextLine()));
                break;
            case 9:
                System.out.print("Enter new Fuel Type: ");
                car.setFuelType(FuelType.valueOf(scanner.nextLine().toUpperCase()));
                break;
            default:
                System.out.println("Invalid choice, please select a valid option.");
                break;
        }
    }
}
