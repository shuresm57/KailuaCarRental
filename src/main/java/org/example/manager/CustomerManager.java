package org.example.manager;


import org.example.customer.Address;
import org.example.customer.Customer;
import org.example.db.CustomerDAO;
import org.example.util.AddressCreationHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class CustomerManager {

    private Scanner scanner = new Scanner(System.in);

    private AddressCreationHelper addressManager = new AddressCreationHelper();
    private CustomerDAO customerDAO = new CustomerDAO();


    public void getCustomersFromSQL(Map<String,Customer> customerMap){
        customerDAO.loadCustomers(customerMap);
    }

    public void printCustomer(Map<String,Customer> customerMap){
        customerMap.values().forEach(System.out::print);
    }

    public void deleteCustomer(Map<String,Customer> customerMap){
        Customer deleteCustomer = findCustomer(customerMap);
        customerDAO.deleteCustomer(deleteCustomer);
        customerMap.remove(deleteCustomer.getLicenseNo());
    }

    public Customer findCustomer(Map<String,Customer> customerMap){
        System.out.println("Type the name of the customer: ");
        String name = scanner.nextLine();
        for(Customer customer : customerMap.values()){
            if(customer.getName().contains(name)){
                return customer;
            }
        }
        System.out.println("The customer was not found in the list.");
        return null;
    }

    public Customer createCustomer(Map<String,Customer> customerMap){
        System.out.println("Customer Name: ");
        String name = scanner.nextLine();
        System.out.println("Customer Address: ");
        Address address = addressManager.createAddress();
        System.out.println("Phone Number: ");
        String phoneNo = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("License Number: ");
        String licenseNo = scanner.nextLine();
        System.out.println("Enter license registration date (dd-MM-yyyy): ");
        String dateAsString = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate driverSince = LocalDate.parse(dateAsString, formatter);
        Customer customer = new Customer(name, address, phoneNo, email, licenseNo, driverSince);
        customerMap.put(customer.getLicenseNo(),customer);
        System.out.println("New Customer added successfully!");
        return customer;
    }

    public void updateCustomer(Map<String,Customer> customerMap){
        AddressCreationHelper addressManager = new AddressCreationHelper();
        Customer updateCustomer = findCustomer(customerMap);

        System.out.println("Choose what you would like to update: " +
                "\n1. Name" +
                "\n2. Phone Number" +
                "\n3. Email" +
                "\n4. License Number" +
                "\n5. Address" +
                "\n6. License Registration Date");


        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                updateCustomer.setName(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new number: ");
                updateCustomer.setPhoneNo(scanner.nextLine());
                break;
            case 3:
                System.out.print("Enter new email: ");
                updateCustomer.setEmail(scanner.nextLine());
                break;
            case 4:
                System.out.print("Enter new license number: ");
                updateCustomer.setLicenseNo(scanner.nextLine());
                break;
            case 5:
                Address newAddress = addressManager.createAddress();
                updateCustomer.setAddress(newAddress);
                break;
            case 6:
                System.out.print("Enter new date of registration: ");
                updateCustomer.setDriverSince(LocalDate.parse(scanner.nextLine()));
                break;
            default:
                System.out.println("Invalid choice, please select a valid option.");
                break;
        }
    }
}
