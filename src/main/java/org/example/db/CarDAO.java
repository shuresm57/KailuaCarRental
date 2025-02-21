package org.example.db;

import org.example.car.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/* En DAO (Data Access Object) som tager sig af alle
 * SQL queries af objektet Car.
 * Når den henter data fra databasen, generer den objekter
 * af Car og gør brug af polyformi til at indsætte biler i
 * en liste af Car*/

public class CarDAO {

    private Connection connection = SQLDriver.connect();

    public CarDAO(){}

    public void loadFamilyCars(Map<String, Car> carMap){
        try{
            String query = """
                            SELECT * FROM car WHERE car_type = 'FAMILY'
                            """;
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
                carMap.put(familyCar.getRegNo(), familyCar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSportCars(Map<String, Car> carMap){
        try{
            String query = """
                            SELECT * FROM car WHERE car_type = 'SPORT'
                            """;
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
                carMap.put(sportCar.getRegNo(), sportCar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadLuxuryCars(Map<String, Car> carMap){
        try{
            String query = """
                            SELECT * FROM car WHERE car_type = 'LUXURY'
                            """;
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
                carMap.put(luxuryCar.getRegNo(), luxuryCar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCars(Map<String, Car> carMap){
        String saveCar = """
                            INSERT INTO car (car_brand, car_model, car_plate, car_fuel, car_reg_year, car_reg_month,
                            car_odometer, car_type, car_status, engine_size, horsepower, seats,
                            automatic_gear, air_condition, cruise_control, leather_seats) 
                            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                            ON DUPLICATE KEY UPDATE 
                            car_brand = VALUES(car_brand), car_model = VALUES(car_model), car_plate = VALUES(car_plate), 
                            car_fuel = VALUES(car_fuel), car_reg_year = VALUES(car_reg_year), car_reg_month = VALUES(car_reg_month),
                            car_odometer = VALUES(car_odometer), car_type = VALUES(car_type), car_status = VALUES(car_status),
                            engine_size = VALUES(engine_size), horsepower = VALUES(horsepower), seats = VALUES(seats),
                            automatic_gear = VALUES(automatic_gear), air_condition = VALUES(air_condition), 
                            cruise_control = VALUES(cruise_control), leather_seats = VALUES(leather_seats)
                        """;

        try (PreparedStatement ps = connection.prepareStatement(saveCar)) {
            for (Car car : carMap.values()) {
                String type = "";

                if (car instanceof SportCar) {
                    type = "SPORT";
                } else if (car instanceof FamilyCar) {
                    type = "FAMILY";
                } else if (car instanceof LuxuryCar) {
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
        } catch (SQLException e) {
            throw new RuntimeException("Error saving cars: ", e);
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