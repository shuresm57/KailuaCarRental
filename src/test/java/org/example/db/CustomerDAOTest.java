package org.example.db;

import org.example.customer.Address;
import org.example.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOTest {

    private Map<String,Customer> customerMap;
    private CustomerDAO customerDAO;
    private Customer customer;
    private Address address;

    @BeforeEach
    public void setUp(){
        customerMap = new HashMap<>();
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
        customerMap.put(customer.getLicenseNo(),customer);
        assertDoesNotThrow(() -> customerDAO.saveCustomers(customerMap));
    }
    @Test
    public void getCustomersTest() {
        customerMap.put(customer.getLicenseNo(),customer);
        assertDoesNotThrow(() -> customerDAO.loadCustomers(customerMap));
    }
}