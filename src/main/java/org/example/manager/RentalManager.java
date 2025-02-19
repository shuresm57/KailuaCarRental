package org.example.manager;

import org.example.car.Car;
import org.example.customer.Customer;
import org.example.dao.RentalDAO;
import org.example.rental.Rental;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class RentalManager {

    private Scanner scanner = new Scanner(System.in);
    private RentalDAO rentalDAO = new RentalDAO();
    private CustomerManager customerManager = new CustomerManager();
    private CarManager carManager = new CarManager();

    public void getRentalsFromSQL(ArrayList<Rental> rentalList, ArrayList<Customer> customerList,ArrayList<Car> carList){
        rentalDAO.getRentals(rentalList,carList,customerList);
    }

    public void printRentals(ArrayList<Rental> rentalList){
        rentalList.forEach(System.out::print);
    }

    public void deleteRental(ArrayList<Rental> rentalList){
        Rental deleteRental = findRental(rentalList);
        rentalDAO.deleteRental(deleteRental);
        rentalList.remove(deleteRental);
    }

    public Rental findRental(ArrayList<Rental> rentalList){
        System.out.println("Type the name of the customer: ");
        String name = scanner.nextLine();
        for(Rental rental : rentalList){
            if(rental.getCustomer().getName().contains(name)){
                return rental;
            }
        }
        System.out.println("The rental or customer was not found in the list.");
        return null;
    }

    public Customer checkCustomerExist(ArrayList<Customer> customerList) {
        Customer customer = customerManager.findCustomer(customerList);

        if (customer == null) {
            System.out.println("Customer not found. Creating a new customer...");
            customerManager.createCustomer(customerList);
            return customerList.get(customerList.size() - 1); // Return the newly added customer
        } else {
            System.out.println("Customer found: " + customer.getName() +
                    "\nWould you like to use this customer or create new?" +
                    "\n1. Use this customer" +
                    "\n2. Create new");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.println("Using existing customer: " + customer.getName());
                    return customer;
                }
                case 2 -> {
                    customerManager.createCustomer(customerList);
                    return customerList.get(customerList.size() - 1);
                }
                default -> {
                    System.out.println("Invalid choice. Using existing customer.");
                    return customer;
                }
            }
        }
    }

    public void createRental(ArrayList<Rental> rentalList, ArrayList<Customer> customerList, ArrayList<Car> carList){
        Customer customer = checkCustomerExist(customerList);

        carManager.printAvailableCars(carList);
        Car car = carManager.findCar(carList);

        System.out.println("Enter start of rental date (dd-MM-yyyy): ");
        String startDateAsString = scanner.nextLine();

        System.out.println("Enter end of rental date (dd-MM-yyyy): ");
        String endDateAsString = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate endDate = LocalDate.parse(endDateAsString, formatter);
        LocalDate startDate = LocalDate.parse(startDateAsString, formatter);

        System.out.println("Enter amount of max km allowed: ");
        int maxKM = Integer.parseInt(scanner.nextLine());

        Rental rental = new Rental(customer,car,startDate,endDate,maxKM);

        rentalList.add(rental);
    }

    public void updateRental(ArrayList<Rental> rentalList) {
        Rental rental = findRental(rentalList); // Find the rental to update

        System.out.println( "Choose what you would like to update: " +
                            "\n1. Start Date" +
                            "\n2. End Date" +
                            "\n3. Max KM" +
                            "\n4. End Rental");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                System.out.print("Enter new start date (YYYY-MM-DD): ");
                rental.setStartDate(LocalDate.parse(scanner.nextLine())); // Set the new start date
                break;
            case 2:
                System.out.print("Enter new end date (YYYY-MM-DD): ");
                rental.setEndDate(LocalDate.parse(scanner.nextLine())); // Set the new end date
                break;
            case 3:
                System.out.print("Enter new max km: ");
                rental.setMaxKM(Integer.parseInt(scanner.nextLine())); // Set the new max km
                break;
            case 5:
                System.out.println("Ending rental and marking as completed.");

                System.out.print("Enter the kilometers driven: ");
                int kmDriven = Integer.parseInt(scanner.nextLine());

                Rental completedRental = new Rental(rental.getCustomer(), rental.getCar(), rental.getStartDate(), rental.getEndDate(), rental.getMaxKM());
                completedRental.endRental(completedRental, kmDriven);

                rentalList.remove(rental);
                rentalList.add(completedRental);

                System.out.println("Rental has been marked as completed with " + kmDriven + " km driven.");
                break;
            default:
                System.out.println("Invalid choice, please select a valid option.");
                break;
        }
    }

}
