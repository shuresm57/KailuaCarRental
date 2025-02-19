package org.example.dao;

import org.example.car.*;
import org.example.sql.SQLDriver;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/* En DAO (Data Access Object) som tager sig af alle
* database handlinger af objektet Car.
* Når den henter data fra databasen, generer den objekter
* af Car og gør brug af polyformi til at indsætte biler i
* en liste af Car*/

public class CarDAO {

    private Connection connection = SQLDriver.connect();

    public CarDAO(){}

    public void getFamilyCars(ArrayList<Car> carList){
        try{
            String query = "SELECT * FROM car WHERE car_type = 'FAMILY'";
            Statement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                FuelType fuelType = FuelType.valueOf(rs.getString("car_fuel"));
                CarStatus carStatus = CarStatus.valueOf(rs.getString("car_status"));

                Car familyCar = new FamilyCar(
                        rs.getString("car_brand"),
                        rs.getString("car_model"),
                        rs.getString("car_plate"),
                        rs.getInt("car_odometer"),
                        rs.getInt("engine_size"),
                        rs.getInt("seats"),
                        rs.getInt("horsepower"),
                        rs.getBoolean("cruise_control"),
                        LocalDate.of(rs.getInt("car_reg_year"), rs.getInt("car_reg_month"), 1),
                        fuelType,
                        carStatus
                );
                carList.add(familyCar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getSportCars(ArrayList<Car> carList){
        try{
            String query = "SELECT * FROM car WHERE car_type = 'SPORT'";
            Statement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                FuelType fuelType = FuelType.valueOf(rs.getString("car_fuel"));
                CarStatus carStatus = CarStatus.valueOf(rs.getString("car_status"));

                Car sportCar = new SportCar(
                        rs.getString("car_brand"),
                        rs.getString("car_model"),
                        rs.getString("car_plate"),
                        rs.getInt("car_odometer"),
                        rs.getInt("engine_size"),
                        rs.getInt("horsepower"),
                        LocalDate.of(rs.getInt("car_reg_year"), rs.getInt("car_reg_month"), 1),
                        fuelType,
                        carStatus

                );
                carList.add(sportCar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getLuxuryCars(ArrayList<Car> carList){
        try{
            String query = "SELECT * FROM car WHERE car_type = 'LUXURY'";
            Statement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                FuelType fuelType = FuelType.valueOf(rs.getString("car_fuel"));
                CarStatus carStatus = CarStatus.valueOf(rs.getString("car_status"));

                Car luxuryCar = new LuxuryCar(
                        rs.getString("car_brand"),
                        rs.getString("car_model"),
                        rs.getString("car_plate"),
                        rs.getInt("car_odometer"),
                        rs.getInt("engine_size"),
                        rs.getInt("horsepower"),
                        rs.getInt("seats"),
                        LocalDate.of(rs.getInt("car_reg_year"), rs.getInt("car_reg_month"), 1),
                        fuelType,
                        carStatus
                );
                carList.add(luxuryCar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCars(ArrayList<Car> carList){
        try {
            String[] columns = {
                    "car_brand", "car_model", "car_plate", "car_fuel", "car_reg_year", "car_reg_month",
                    "car_odometer", "car_type", "car_status", "engine_size", "horsepower", "seats",
                    "automatic_gear", "air_condition", "cruise_control", "leather_seats"
            };

            String columnNames = String.join(", ", columns);
            String valuePlaceholders = String.join(", ", Collections.nCopies(columns.length, "?"));
            String updateClause = String.join(", ", Arrays.stream(columns)
                    .map(col -> col + " = VALUES(" + col + ")")
                    .toArray(String[]::new));

            String save = String.format(
                    "INSERT INTO car (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s",
                    columnNames, valuePlaceholders, updateClause
            );

            PreparedStatement ps = connection.prepareStatement(save);

            for (Car car : carList) {

                String type = "";
                if(car instanceof SportCar) {
                    type = "SPORT";
                }
                else if (car instanceof FamilyCar){
                    type = "FAMILY";
                }
                else if (car instanceof LuxuryCar){
                    type = "LUXURY";
                }

                ps.setString(1, car.getBrand());
                ps.setString(2, car.getModel());
                ps.setString(3, car.getRegNo());
                ps.setString(4, car.getFuelType());
                ps.setInt(5, car.getRegYear());
                ps.setInt(6, car.getRegMonth());
                ps.setInt(7, car.getOdometer());
                ps.setString(8, type);
                ps.setString(9, car.getCarStatus());
                ps.setDouble(10, car.getEngineSize());
                ps.setInt(11, car.getHorsepower());
                ps.setInt(12, car.getSeats());
                ps.setBoolean(13, car.isAutomaticGear());
                ps.setBoolean(14, car.isAirCondition());
                ps.setBoolean(15, car.isCruiseControl());
                ps.setBoolean(16, car.isLeatherSeats());

                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCar(Car c){
        try {
            String query = "DELETE FROM car WHERE car_plate = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, c.getRegNo());
            System.out.println("Car with plate " + c.getRegNo() + " has been deleted.");
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting car: " + e.getMessage(), e);
        }
    }
}

