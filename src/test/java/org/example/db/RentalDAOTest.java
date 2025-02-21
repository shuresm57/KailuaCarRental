package org.example.db;

import org.example.car.Car;
import org.example.car.CarStatus;
import org.example.car.FamilyCar;
import org.example.car.FuelType;
import org.example.customer.Address;
import org.example.customer.Customer;
import org.example.rental.Rental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;

public class RentalDAOTest {

    private Rental rentalTest;

    private Customer customerTest;
    private Address addressTest;

    private FuelType fuelType;
    private Car familyCarTest, sportCarTest, luxuryCarTest;
    private CarStatus carStatus;

    private CarDAO carDAO;
    private CustomerDAO customerDAO;
    private RentalDAO rentalDAO;

    private Map<String,Car> carMap;
    private Map<String,Customer> customerMap;
    private ArrayList<Rental> rentalList;
    private Connection connection;


    @BeforeEach
        public void setUp() {
            addressTest = new Address("Street 1", "1000", "Copenhagen");
            customerTest =  new Customer("John Doe", addressTest, "12345678", "john@mail.com", "LIC123", LocalDate.of(2015, 6, 15));
            familyCarTest = new FamilyCar(
                    "Tesla",
                    "Roadster",
                    "123456",
                    100,
                    100,
                    2,
                    10,
                    true,
                    LocalDate.of(2022, 1, 1),
                    FuelType.ELECTRIC,
                    CarStatus.AVAILABLE
            );
            rentalTest = new Rental(customerTest,familyCarTest,LocalDate.of(2025, 1, 1),LocalDate.of(2025, 1, 20),200);
            carMap = new HashMap<>();
            customerMap = new HashMap<>();
            rentalList = new ArrayList<>();
            rentalDAO = new RentalDAO();
            carDAO = new CarDAO();
            customerDAO = new CustomerDAO();
            connection = SQLDriver.connection();
            }


        @Test
        public void rentalCreationTest(){
            assertNotNull(addressTest);
            assertNotNull(customerTest);
            assertNotNull(familyCarTest);
            assertNotNull(rentalTest);
        }

        @Test
        public void loadRentalTest(){
            assertDoesNotThrow(() -> rentalDAO.loadRentals(rentalList,customerMap,carMap));
        }

        @Test
        public void saveRentalTest() {
            assertDoesNotThrow(() -> rentalDAO.saveRentals(rentalList));
        }
    }