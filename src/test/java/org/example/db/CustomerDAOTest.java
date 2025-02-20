package org.example.db;

import org.example.customer.Address;
import org.example.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOTest {

    private ArrayList<Customer> customerList;
    private CustomerDAO customerDAO;
    private Customer customer;
    private Address address;

    @BeforeEach
    public void setUp(){
        customerList = new ArrayList<>();
        customerDAO = new CustomerDAO();
        address = new Address("Street 1", "1000", "Copenhagen");
        customer =  new Customer("John Doe", address, "12345678", "john@mail.com", "LIC123", LocalDate.of(2015, 6, 15));
    }
    @Test
    public void testCustomerCreation(){
        assertNotNull(customer);
        assertNotNull(address);
    }
    @Test
    public void saveCustomerTest() {
        customerList.add(customer);
        assertDoesNotThrow(() -> customerDAO.saveCustomers(customerList));
    }
    @Test
    public void getCustomersTest() {
        customerList.add(customer);
        assertDoesNotThrow(() -> customerDAO.loadCustomers(customerList));
    }
}