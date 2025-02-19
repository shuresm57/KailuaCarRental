package org.example.manager;

import org.example.customer.Address;

import java.util.Scanner;

public class AddressManager {

    private Scanner scanner = new Scanner(System.in);

    public AddressManager(){}

    public Address createAddress(){
        System.out.println("Street name and number: ");
        String street = scanner.nextLine();
        System.out.println("Zip: ");
        String zip = scanner.nextLine();
        System.out.println("City: ");
        String city = scanner.nextLine();
        return new Address(street,zip,city);
    }


}
