package org.example.menu;

import org.example.car.Car;
import org.example.db.CustomerDAO;
import org.example.db.RentalDAO;
import org.example.customer.Customer;
import org.example.manager.CarManager;
import org.example.manager.CustomerManager;
import org.example.manager.RentalManager;
import org.example.rental.Rental;
import org.example.db.SQLDriver;


import java.util.*;

public class Menu {

    private static final         Map<String,Car>             carMap              = new HashMap<>();
    private static final         Map<String,Customer>        customerMap         = new HashMap<>();
    private static final         List<Rental>                rentalList          = new ArrayList<>();

    private static final         Scanner                     scanner             = new Scanner(System.in);

    private static final         CarManager                  carManager          = new CarManager();
    private static final         CustomerManager             customerManager     = new CustomerManager();
    private static final         RentalManager               rentalManager       = new RentalManager();

    //Hovedmenuen og alle submenuer er i et infinite loop, indtil at denne boolean er sat til = false; (Når programmet skal lukkes)
    private static boolean                                  running             = true;

    //metode der kører programmet, først loader den al nødvendig data fra SQL, derefter kører metoden der indeholder menuen
    public static void runProgram(){
        loadFromSQL();
        mainMenu();
    }

    public static void loadFromSQL(){
        carManager.loadCarsFromSQL(carMap);
        customerManager.loadCustomersFromSQL(customerMap);
        rentalManager.loadRentalsFromSQL(rentalList, customerMap, carMap);
    }

    public static void closeProgram(){
        running = false;
        try{
            customerManager.saveCustomers(customerMap);
            carManager.saveCars(carMap);
            rentalManager.saveRentals(rentalList);
        }
        finally{
                SQLDriver.closeConnection();
        }
    }

    //Hjælpemetode der hjælper med alle switch cases. Returnerer den int, som er valget på menuen
    public static int getIntChoice(int max) {
        int min = 0;
        String prompt = """
                               Select an option: 
                """;
        while (true) {
            try {
                System.out.print(prompt);
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;  // Return valid choice within range
                } else {
                    System.out.println("Invalid choice, please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a valid number.");
            }
        }
    }


    public static void mainMenu() {
        while(running){

            System.out.println(asciiArt());
            System.out.println("""
                                                Main Menu
                                                1. Car Menu
                                                2. Rental Menu
                                                3. Customer Menu
                                                0. Save and Exit
                                """);

            switch (getIntChoice(6)) {
                case 1 -> carMenu();
                case 2 -> rentalMenu();
                case 3 -> customerMenu();
                case 0 -> closeProgram();
                default -> System.out.println("Invalid choice. Try again.\n");
            }
        }
    }

    public static void carMenu(){
        while(running) {
            System.out.println("""
                                                Car Menu
                                                1. Create Car
                                                2. See all cars
                                                3. See all available cars
                                                4. See all currently rented cars
                                                5. Update Car
                                                6. Delete Car
                                                0. Main Menu
                                 """);

            switch (getIntChoice(6)) {
                case 1 -> carManager.createCar(carMap);
                case 2 -> carManager.printCars(carMap);
                case 3 -> carManager.printAvailableCars(carMap);
                case 4 -> carManager.printRentedCars(rentalList);
                case 5 -> carManager.updateCar(carMap);
                case 6 -> carManager.deleteCar(carMap);
                case 0 -> {
                    mainMenu();
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.\n");
            }
        }
    }

    public static void rentalMenu(){
        while(running){
            System.out.println("""
                                                Rental Menu
                                                1. Create rental
                                                2. See All rentals
                                                3. Update rental
                                                4. Delete rental
                                                0. Main Menu
                                """);

            switch (getIntChoice(6)) {
                case 1 -> rentalManager.createRental(rentalList, customerMap, carMap);
                case 2 -> rentalManager.printRentals(rentalList);
                case 3 -> rentalManager.updateRental(rentalList);
                case 4 -> rentalManager.deleteRental(rentalList);
                case 0 -> mainMenu();
                default -> System.out.println("Invalid choice. Try again.\n");
            }
        }
    }

    public static void customerMenu(){
        while(running){
            System.out.println("""
                                                Customer Menu
                                                1. Create Customer
                                                2. See All Customers
                                                3. Update Customer Information
                                                4. Delete Customer
                                                0. Main Menu
                                """);

            switch (getIntChoice(4)) {
                case 1 -> customerManager.createCustomer(customerMap);
                case 2 -> customerManager.printCustomer(customerMap);
                case 3 -> customerManager.updateCustomer(customerMap);
                case 4 -> customerManager.deleteCustomer(customerMap);
                case 0 -> mainMenu();
                default -> System.out.println("Invalid choice. Try again.\n");
            }
        }
    }

    public static String asciiArt(){
        String ascii =
"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
"~         _  __     _ _                        ~\n" +
"~        | |/ /__ _(_) |_   _  __ _            ~\n" +
"~        | ' // _` | | | | | |/ _` |           ~\n" +
"~        | . \\ (_| | | | |_| | (_| |           ~\n" +
"~  ____  |_|\\_\\__,_|_|_|\\__,_|\\__,_|_        _ ~\n" +
"~ / ___|__ _ _ __  |  _ \\ ___ _ __ | |_ __ _| |~\n" +
"~| |   / _` | '__| | |_) / _ \\ '_ \\| __/ _` | |~\n" +
"~| |__| (_| | |    |  _ <  __/ | | | || (_| | |~\n" +
"~ \\____\\__,_|_|    |_| \\_\\___|_| |_|\\__\\__,_|_|~\n" +
"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
        return ascii;
    }


}
