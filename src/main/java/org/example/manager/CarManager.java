package org.example.manager;

import org.example.car.*;
import org.example.db.CarDAO;
import org.example.rental.Rental;
import org.example.util.CarHelper;

import java.util.*;

/*Denne liste fungerer som en mellemand mellem min TUI og forretningen
 * og set fra GRASP princippet om Controller, så opfylder den denne rolle.
 * Den opfylder også Pure Fabrication, da den håndterer logik
 * og reducerer kompleksitet i andre klasser.*/

public class CarManager {

    private         Scanner         scanner         = new Scanner(System.in);
    private         LuxuryManager   luxuryManager   = new LuxuryManager();
    private         SportManager    sportManager    = new SportManager();
    private         FamilyManager   familyManager   = new FamilyManager();
    private         CarDAO          carDAO          = new CarDAO();

    public CarManager(){}

    public void updateCar(Map<String, Car> carMap){
        Car updateCar = findCar(carMap);
        switch(updateCar.getClass().getSimpleName()){
            case "FamilyCar"    -> familyManager.updateFamilyCar(updateCar);
            case "LuxuryCar"    -> luxuryManager.updateLuxuryCar(updateCar);
            case "SportCar"     -> sportManager.updateSportCar(updateCar);
            default -> System.out.println("Car class could not be found.");
        }
    }

    public void printCars(Map<String, Car> carMap){
        carMap.values().forEach(System.out::print);
    }

    public void printAvailableCars(Map<String, Car> carMap){
        carMap.values().stream()
                .filter(car -> car.getCarStatus().equals("AVAILABLE"))
                .forEach(System.out::print);
    }

    public void printRentedCars(List<Rental> rentals) {
        for(Rental rental : rentals){
            System.out.println(rental.getCar() + " " + rental.getCustomer());
        }
    }
    /*public void printRentedCars(Map<String, Car> carMap, Map<String, Customer> customerMap){
        carMap.values().stream()
                .filter(car -> car.getCarStatus().equals("RENTED"))
                .forEach(System.out::print);

    }*/

    public void deleteCar(Map<String, Car> carMap){
        Car deleteCar = findCar(carMap);
        if (deleteCar != null) {
            carDAO.deleteCar(deleteCar);
            carMap.remove(deleteCar.getRegNo());
        }
    }

    public Car findCar(Map<String, Car> carMap){
        System.out.println("Type the registration number of the car: ");
        String regNo = scanner.nextLine();
        Car car = carMap.get(regNo);
        if (car == null) {
            System.out.println("The car was not found in the list.");
        }
        return car;
    }

    public void createCar(Map<String, Car> carMap){
        System.out.println("Which type of car would you like to create?\n1. Sport Car\n2. Luxury Car\n3. Family Car");
        int choice = Integer.parseInt(scanner.nextLine());
        Car newCar = null;
        CarHelper.startCreation();
        switch(choice){
            case 1 -> newCar = sportManager.createSportCar();
            case 2 -> newCar = luxuryManager.createLuxuryCar();
            case 3 -> newCar = familyManager.createFamilyCar();
            default -> System.out.println("Invalid choice");
        }
        if (newCar != null) {
            carMap.put(newCar.getRegNo(), newCar);
            System.out.println("Car added successfully!");
        }
    }

    public void getCarsFromSQL(Map<String,Car> carMap){
        carDAO.loadFamilyCars(carMap);
        carDAO.loadSportCars(carMap);
        carDAO.loadLuxuryCars(carMap);
    }
}