package org.example.db;

import org.example.car.*;
import org.example.customer.Customer;
import org.example.rental.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.example.car.CarStatus.RENTED;

public class RentalDAO {

    private Connection connection = SQLDriver.connection();



    public void loadRentals(List<Rental> rentalList, Map<String, Customer> customerMap, Map<String, Car> carMap) {
        String query = """
                            SELECT r.start_date, r.end_date, r.rental_status, r.customer_id, 
                                   c.car_plate, r.max_km, r.km_driven, cust.license_no
                            FROM rental r
                            JOIN car c ON r.car_id = c.car_id
                            JOIN customer cust ON r.customer_id = cust.customer_id
                        """;

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rental rental = createRental(rs, customerMap, carMap);
                if (!isRentalInList(rental, rentalList)) {
                    rentalList.add(rental);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching rentals: " + e.getMessage(), e);
        }
    }

    private Rental createRental(ResultSet rs, Map<String, Customer> customerMap, Map<String, Car> carMap) throws SQLException {
        LocalDate startDate = rs.getDate("start_date").toLocalDate();
        LocalDate endDate = rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null;
        int maxKM = rs.getInt("max_km");
        int kmDriven = rs.getInt("km_driven");

        Customer customer = customerMap.get(rs.getString("license_no"));
        if (customer == null) throw new RuntimeException("Customer not found!");

        Car car = carMap.get(rs.getString("car_plate"));
        if (car == null) throw new RuntimeException("Car not found!");

        Rental rental = new Rental(customer, car, startDate, endDate, maxKM);
        car.setCarStatus(RENTED);

        if ("COMPLETED".equals(rs.getString("rental_status"))) {
            rental.endRental(rental, kmDriven);
        }

        return rental;
    }

    private boolean isRentalInList(Rental rental, List<Rental> rentalList) {
        for(Rental r : rentalList){
            if(r.getCar().getRegNo().equals(rental.getCar().getRegNo())){
                return true;
            }
        }
        return false;
    }

    public void saveRentals(List<Rental> rentalList) {
        String saveRental = """
                                INSERT INTO rental (customer_id, car_id, start_date, end_date, rental_status)
                                VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE
                                start_date = VALUES(start_date), end_date = VALUES(end_date), rental_status = VALUES(rental_status)
                            """;

        try (PreparedStatement psRental = connection.prepareStatement(saveRental);
             PreparedStatement psCustomerId = connection.prepareStatement("SELECT customer_id FROM customer WHERE license_no = ?");
             PreparedStatement psCarId = connection.prepareStatement("SELECT car_id FROM car WHERE car_plate = ?")) {

            for (Rental rental : rentalList) {
                int customerId = getId(psCustomerId, rental.getCustomer().getLicenseNo(), "customer_id");
                int carId = getId(psCarId, rental.getCar().getRegNo(), "car_id");

                psRental.setInt(1, customerId);
                psRental.setInt(2, carId);
                psRental.setDate(3, Date.valueOf(rental.getStartDate()));
                psRental.setDate(4, rental.getEndDate() != null ? Date.valueOf(rental.getEndDate()) : null);
                psRental.setString(5, rental.getRentalStatus().name());
                psRental.addBatch();
            }

            psRental.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving rentals: " + e.getMessage(), e);
        }
    }


    private int getId(PreparedStatement ps, String param, String columnName) throws SQLException {
        ps.setString(1, param);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(columnName) : 0;
        }
    }

    public void deleteRental(Rental rental){
        try {
            String query = "DELETE FROM Rental WHERE car_id = (SELECT car_id FROM Car WHERE car_plate = ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, rental.getCar().getRegNo());
            System.out.println("Rental with car license plate " + rental.getCar().getRegNo() + " has been deleted.");
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting rental: " + e.getMessage(), e);
        }
    }


}
