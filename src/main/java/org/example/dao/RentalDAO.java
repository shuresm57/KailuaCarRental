package org.example.dao;

import org.example.car.*;
import org.example.customer.Address;
import org.example.customer.Customer;
import org.example.rental.*;
import org.example.sql.SQLDriver;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class RentalDAO {

    private Connection connection = SQLDriver.connect();


    public void getRentals(ArrayList<Rental> rentalList, ArrayList<Car> carList, ArrayList<Customer> customerList) {
        try {
            String query =  "SELECT r.start_date, r.end_date, r.rental_status, " +
                    "r.customer_id, c.car_plate, r.max_km, r.km_driven, customer.license_no " +
                    "FROM rental r " +
                    "JOIN car c ON r.car_id = c.car_id " +
                    "JOIN customer ON r.customer_id = customer.customer_id";

            Statement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                LocalDate startDate = rs.getDate("start_date").toLocalDate();
                LocalDate endDate = rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null;
                int maxKM = rs.getInt("max_km");
                int kmDriven = rs.getInt("km_driven");

                String licenseNo = rs.getString("license_no");
                Customer customer = null;
                for (Customer c : customerList) {
                    if (c.getLicenseNo().equals(licenseNo)) {
                        customer = c;
                        break;
                    }
                }

                if (customer == null) {
                    throw new RuntimeException("Customer with license number " + licenseNo + " not found!");
                }

                String carPlate = rs.getString("car_plate");
                Car car = null;
                for (Car c : carList) {
                    if (c.getRegNo().equals(carPlate)) {
                        car = c;
                        break;
                    }
                }

                if (car == null) {
                    throw new RuntimeException("Car with license plate " + carPlate + " not found!");
                }

                Rental rental = new Rental(customer, car, startDate, endDate, maxKM);

                boolean alreadyAdded = false;
                for (Rental r : rentalList) {
                    if (r.getCustomer().getLicenseNo().equals(customer.getLicenseNo()) &&
                            r.getCar().getRegNo().equals(car.getRegNo()) &&
                            r.getStartDate().equals(startDate)) {
                        alreadyAdded = true;
                        break;
                    }
                }

                if (!alreadyAdded) {
                    String rentalStatus = rs.getString("rental_status");
                    if ("COMPLETED".equals(rentalStatus)) {
                        rental.endRental(rental, kmDriven);
                    }
                    rentalList.add(rental);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching rentals: " + e.getMessage(), e);
        }
    }

    public void saveRentals(ArrayList<Rental> rentalList) {

        String saveRental = "INSERT INTO rental (customer_id, car_id, start_date, end_date, rental_status) " +
                "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE " +
                "customer_id = VALUES(customer_id), car_id = VALUES(car_id), start_date = VALUES(start_date), " +
                "end_date = VALUES(end_date), rental_status = VALUES(rental_status)";

        String getCustomerIdQuery = "SELECT customer_id FROM customer WHERE email = ?";

        String getCarIdQuery = "SELECT car_id FROM car WHERE car_plate = ?";

        try (PreparedStatement psRental = connection.prepareStatement(saveRental);
             PreparedStatement psCustomerId = connection.prepareStatement(getCustomerIdQuery);
             PreparedStatement psCarId = connection.prepareStatement(getCarIdQuery)) {

            for (Rental rental : rentalList) {

                psCustomerId.setString(1, rental.getCustomer().getEmail());
                ResultSet rsCustomer = psCustomerId.executeQuery();
                int customerId = 0;
                if (rsCustomer.next()) {
                    customerId = rsCustomer.getInt("customer_id");
                }


                psCarId.setString(1, rental.getCar().getRegNo());
                ResultSet rsCar = psCarId.executeQuery();
                int carId = 0;
                if (rsCar.next()) {
                    carId = rsCar.getInt("car_id");
                }


                psRental.setInt(1, customerId);
                psRental.setInt(2, carId);
                psRental.setDate(3, Date.valueOf(rental.getStartDate()));
                psRental.setDate(4, rental.getEndDate() != null ? Date.valueOf(rental.getEndDate()) : null);  // end_date (null check)
                psRental.setString(5, rental.getRentalStatus().name());

                psRental.addBatch();
            }

            psRental.executeBatch();


        } catch (SQLException e) {
            throw new RuntimeException("Error saving rentals: " + e.getMessage(), e);
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
