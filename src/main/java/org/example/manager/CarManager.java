package org.example.manager;

import org.example.car.*;
import org.example.dao.CarDAO;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/*Denne liste fungerer som en mellemand mellem min TUI og forretningen
* og set fra GRASP princippet om Controller, så opfylder den denne rolle.
* Den opfylder også Pure Fabrication, da den håndterer logik
* og reducerer kompleksitet i andre klasser.*/

public class CarManager {

    private Scanner scanner = new Scanner(System.in);
    private LuxuryManager luxuryManager = new LuxuryManager();
    private SportManager sportManager = new SportManager();
    private FamilyManager familyManager = new FamilyManager();
    private CarDAO carDAO = new CarDAO();

    public CarManager(){}

    public void updateCar(ArrayList<Car> carList){
        Car updateCar = findCar(carList);
        if(updateCar instanceof LuxuryCar){
            luxuryManager.updateLuxuryCar(updateCar);
        }
        else if(updateCar instanceof FamilyCar){
            familyManager.updateFamilyCar(updateCar);
        }
        else if(updateCar instanceof SportCar){
            sportManager.updateSportCar(updateCar);
        }
    }

    public void printCars(ArrayList<Car> carList){
        carList.forEach(System.out::print);
    }

    public void printAvailableCars(ArrayList<Car> carList){
        carList.stream()
                .filter(car -> car.getCarStatus().equals("AVAILABLE"))
                .forEach(System.out::print);
    }

    public void printRentedCars(ArrayList<Car> carList){
        carList.stream()
                .filter(car -> car.getCarStatus().equals("RENTED"))
                .forEach(System.out::print);
    }

    public void deleteCar(ArrayList<Car> carList){
        Car deleteCar = findCar(carList);
        carDAO.deleteCar(deleteCar);
        carList.remove(deleteCar);
    }

    public Car findCar(ArrayList<Car> carList){
        System.out.println("Type the registration number of the car: ");
        String regNo = scanner.nextLine();
        for(Car car : carList){
            if(car.getRegNo().equalsIgnoreCase(regNo)){
                return car;
            }
        }
        System.out.println("The car was not found in the list.");
        return null;
    }

    public void createCar(ArrayList<Car> carList){
        System.out.println("Which type of car would you like to create?\n1. Sport Car\n2. Luxury Car\n3. Family Car");
        int choice = Integer.parseInt(scanner.nextLine());
        switch(choice){
            case 1:
                CarCreationHelper.startCreation();
                sportManager.createSportCar();
                carList.add(sportManager.createSportCar());
                break;
            case 2:
                CarCreationHelper.startCreation();
                luxuryManager.createLuxuryCar();
                carList.add(luxuryManager.createLuxuryCar());
                break;
            case 3:
                CarCreationHelper.startCreation();
                familyManager.createFamilyCar();
                carList.add(familyManager.createFamilyCar());
                break;
            default:
                System.out.println("Invalid choice");
        }
        System.out.println("Car added successfully!");
    }


    public void getCarsFromSQL(ArrayList<Car> carList){
        carDAO.getFamilyCars(carList);
        carDAO.getSportCars(carList);
        carDAO.getLuxuryCars(carList);
    }
}
