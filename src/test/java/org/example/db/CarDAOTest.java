package org.example.db;

import org.example.car.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarDAOTest {

    private FuelType fuelType;
    private Map<String,Car> carMap;
    private CarDAO carDAO;
    private Car familyCarTest, sportCarTest, luxuryCarTest;
    private CarStatus carStatus;

    @BeforeEach
    void setUp(){
        carMap = new HashMap<>();
        carDAO = new CarDAO();
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

        sportCarTest = new SportCar(
                "Ferrari",
                "488",
                "789123",
                500,
                3500,
                500,
                LocalDate.of(2020, 5, 15),
                FuelType.GASOLINE,
                CarStatus.AVAILABLE
        );

        luxuryCarTest = new LuxuryCar(
                "Mercedes",
                "S-Class",
                "456789",
                200,
                3000,
                400,
                4,
                LocalDate.of(2021, 3, 25),
                FuelType.DIESEL,
                CarStatus.RENTED
        );
        carMap.put(familyCarTest.getRegNo(),familyCarTest);
        carMap.put(sportCarTest.getRegNo(),sportCarTest);
        carMap.put(luxuryCarTest.getRegNo(),luxuryCarTest);
    }

    @Test
    public void testFamilyCreation(){
        assertNotNull(luxuryCarTest);
    }
    @Test
    public void testSportCreation(){
        assertNotNull(sportCarTest);
    }
    @Test
    public void testLuxuryCreation(){
        assertNotNull(luxuryCarTest);
    }
    @Test
    public void saveCarTest() {
        carMap.put(familyCarTest.getRegNo(),familyCarTest);
        carMap.put(sportCarTest.getRegNo(),sportCarTest);
        carMap.put(luxuryCarTest.getRegNo(),luxuryCarTest);
        assertDoesNotThrow(() -> carDAO.saveCars(carMap));
    }

    @Test
    public void getFamilyCarTest() {
        assertDoesNotThrow(() -> carDAO.loadFamilyCars(carMap));
    }



}