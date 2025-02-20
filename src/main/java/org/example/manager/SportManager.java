package org.example.manager;

import org.example.car.*;
import org.example.util.CarCreationHelper;

import java.time.LocalDate;
import java.util.Scanner;

public class SportManager {

    private Scanner scanner = new Scanner(System.in);

    public SportManager(){}

    public SportCar createSportCar(){
        return new SportCar(
                CarCreationHelper.getBrand(),
                CarCreationHelper.getModel(),
                CarCreationHelper.getRegNo(),
                CarCreationHelper.getOdometer(),
                CarCreationHelper.getEngineSize(),
                CarCreationHelper.getHorsepower(),
                CarCreationHelper.getRegDate(),
                CarCreationHelper.getFuelType(),
                CarStatus.AVAILABLE
        );
    }

    public void updateSportCar(Car car) {
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
                if (Integer.parseInt(scanner.nextLine()) < 200) {
                    System.out.println(scanner.nextLine() + " is not enough HP for a sport car");
                    break;
                }
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
