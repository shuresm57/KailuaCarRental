package org.example.menu;

import org.example.car.Car;
import org.example.customer.Customer;
import org.example.manager.CarManager;
import org.example.dao.*;
import org.example.manager.CustomerManager;
import org.example.manager.RentalManager;
import org.example.rental.Rental;
import org.example.sql.SQLDriver;


import java.util.*;

public class Menu {

    ArrayList<Car> carList = new ArrayList<>();
    ArrayList<Customer> customerList = new ArrayList<>();
    ArrayList<Rental> rentalList = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    CarDAO carDAO = new CarDAO();
    CustomerDAO customerDAO = new CustomerDAO();
    RentalDAO rentalDAO = new RentalDAO();

    CarManager carManager = new CarManager();
    CustomerManager customerManager = new CustomerManager();
    RentalManager rentalManager = new RentalManager();

    public void mainMenu() {

        carManager.getCarsFromSQL(carList);
        customerManager.getCustomersFromSQL(customerList);
        rentalManager.getRentalsFromSQL(rentalList,customerList,carList);

        System.out.println(asciiArt());
        System.out.println("Main Menu");
        System.out.println("1. Car Menu");
        System.out.println("2. Rental Menu");
        System.out.println("3. Customer Menu");
        System.out.println("0. Save and Exit");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> carMenu();
            case 2 -> rentalMenu();
            case 3 -> customerMenu();
            case 0 -> {
                customerDAO.saveCustomers(customerList);
                carDAO.saveCars(carList);
                rentalDAO.saveRentals(rentalList);
                SQLDriver.closeConnection();
            }
            default -> System.out.println("Ugyldigt valg. Prøv igen.\n");
        }
    }

    public void carMenu(){
        System.out.println("Car Menu");
        System.out.println("1. Create Car");
        System.out.println("2. See all cars");
        System.out.println("3. See all available cars");
        System.out.println("4. See all currently rented cars");
        System.out.println("5. Update Car");
        System.out.println("6. Delete Car");
        System.out.println("0. Main Menu");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> {
                carManager.createCar(carList);
                carMenu();
            }
            case 2 -> {
                carManager.printCars(carList);
                carMenu();
            }
            case 3 ->{
                carManager.printAvailableCars(carList);
                carMenu();
            }
            case 4 -> {
                carManager.printRentedCars(carList);
                carMenu();
            }
            case 5 -> {
                carManager.updateCar(carList);
                carMenu();
            }
            case 6 -> {
                carManager.deleteCar(carList);
                carMenu();
            }
            case 0 -> mainMenu();
            default -> {
                System.out.println("Invalid choice. Try again.\n");
                carMenu();
            }
        }
    }

    public void rentalMenu(){
        System.out.println("Rental Menu");
        System.out.println("1. Create rental");
        System.out.println("2. See All rentals");
        System.out.println("5. Update rental");
        System.out.println("6. Delete rental");
        System.out.println("0. Main Menu");
        int choice = Byte.parseByte(scanner.nextLine());
        switch (choice) {
            case 1 -> {
                rentalManager.createRental(rentalList,customerList,carList);
                rentalMenu();
            }
            case 2 -> {
                rentalManager.printRentals(rentalList);
                rentalMenu();
            }
            case 3 -> {
                rentalManager.updateRental(rentalList);
                rentalMenu();
            }
            case 4 -> {
                rentalManager.deleteRental(rentalList);
                rentalMenu();
            }
            case 0 -> mainMenu();
            default -> System.out.println("Invalid choice. Try again.\n");
        }
    }

    public void customerMenu(){
        System.out.println("Customer Menu");
        System.out.println("1. Create Customer");
        System.out.println("2. See All Customers");
        System.out.println("3. Update Customer Information");
        System.out.println("4. Delete Customer");
        System.out.println("0. Main Menu");
        byte choice = Byte.parseByte(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                customerManager.createCustomer(customerList);
                customerMenu();
            }
            case 2 -> {
                customerManager.printCustomer(customerList);
                customerMenu();
            }
            case 3 -> {
                customerManager.updateCustomer(customerList);
                customerMenu();
            }
            case 4 -> {
                customerManager.deleteCustomer(customerList);
                customerMenu();
            }
            case 0 -> mainMenu();
            default -> {
                System.out.println("Invalid choice. Try again.\n");
                customerMenu();
            }
        }
    }

    public String asciiArt(){
        String ascii = "              ____----------- _____\n" +
                "\\~~~~~~~~~~/~_--~~~------~~~~~     \\\n" +
                " `---`\\  _-~      |                   \\\n" +
                "   _-~  <_         |                     \\[]\n" +
                " / ___     ~~--[\"\"] |      ________-------'_\n" +
                "> /~` \\    |-.   `\\~~.~~~~~                _ ~ - _\n" +
                " ~|  ||\\%  |       |    ~  ._                ~ _   ~ ._\n" +
                "   `_//|_%  \\      |          ~  .              ~-_   /\\\n" +
                "          `--__     |    _-____  /\\               ~-_ \\/.\n" +
                "               ~--_ /  ,/ -~-_ \\ \\/          _______---~/\n" +
                "                   ~~-/._<   \\ \\`~~~~~~~~~~~~~     ##--~/\n" +
                "                         \\    ) |`------##---~~~~-~  ) )\n" +
                "                          ~-_/_/                  ~~ ~~\n" +
                "      ___  ____       _      _____ _____ _____  _____   _          \n" +
                "     |_  ||_  _|     / \\    |_   _|_   _|_   _||_   _| / \\         \n" +
                "       | |_/ /      / _ \\     | |   | |   | |    | |  / _ \\        \n" +
                "       |  __'.     / ___ \\    | |   | |   | '    ' | / ___ \\       \n" +
                "      _| |  \\ \\_ _/ /   \\ \\_ _| |_ _| |__/ \\ \\__/ /_/ /   \\ \\_     \n" +
                "     |____||____|____| |____|_____|________|`.__.'|____| |____|    \n" +
                "                                                                   \n" +
                "                    ______       _      _______                    \n" +
                "                  .' ___  |     / \\    |_   __ \\                   \n" +
                "                 / .'   \\_|    / _ \\     | |__) |                  \n" +
                "                 | |          / ___ \\    |  __ /                   \n" +
                "                 \\ `.___.'\\ _/ /   \\ \\_ _| |  \\ \\_                 \n" +
                "                  `.____ .'|____| |____|____| |___|                \n" +
                "                                                                   \n" +
                "      _______    ________ ____  _____ _________    _      _____    \n" +
                "     |_   __ \\  |_   __  |_   \\|_   _|  _   _  |  / \\    |_   _|   \n" +
                "       | |__) |   | |_ \\_| |   \\ | | |_/ | | \\_| / _ \\     | |     \n" +
                "       |  __ /    |  _| _  | |\\ \\| |     | |    / ___ \\    | |   _ \n" +
                "      _| |  \\ \\_ _| |__/ |_| |_\\   |_   _| |_ _/ /   \\ \\_ _| |__/ |\n" +
                "     |____| |___|________|_____|\\____| |_____|____| |____|________|";
        return ascii;
    }


}
