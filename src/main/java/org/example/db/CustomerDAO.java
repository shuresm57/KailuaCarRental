package org.example.db;

import org.example.customer.Address;
import org.example.customer.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class CustomerDAO {

    private Connection connection = SQLDriver.connect();

    public void loadCustomers(Map<String,Customer> customerMap){
        try{
            String query = """
            SELECT * FROM customer
            """;

            Statement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                String name = rs.getString("name");
                String address = rs.getString("address");
                String zipCode = rs.getString("zip_code");
                String phoneNo = rs.getString("phone_no");
                String email = rs.getString("email");
                String licenseNo = rs.getString("license_no");
                java.sql.Date sqlDate = rs.getDate("driver_since");
                LocalDate driverSince = sqlDate.toLocalDate();

                String cityName = "";
                try (PreparedStatement cityStmt = connection.prepareStatement("SELECT city_name FROM city WHERE zip_code = ?")) {
                    cityStmt.setString(1, zipCode);
                    try (ResultSet cityRs = cityStmt.executeQuery()) {
                        if (cityRs.next()) {
                            cityName = cityRs.getString("city_name");
                        }
                    }
                }

                Address customerAddress = new Address(address, zipCode, cityName);
                Customer customer = new Customer(name, customerAddress, phoneNo, email, licenseNo, driverSince);
                customerMap.put(customer.getLicenseNo(),customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCustomers(Map<String,Customer> customerMap) {
        try {
            connection.setAutoCommit(false);

            String saveCity = """ 
                                    INSERT INTO city (city_name, zip_code) 
                                    VALUES (?, ?) ON DUPLICATE KEY UPDATE city_name = VALUES(city_name)
                                    """;

            String saveCustomer = """
                                    INSERT INTO customer (name, phone_no, email, license_no, address, zip_code, driver_since)
                                    VALUES (?, ?, ?, ?, ?, ?, ?)
                                    ON DUPLICATE KEY UPDATE
                                    name = VALUES(name), phone_no = VALUES(phone_no), email = VALUES(email),
                                    license_no = VALUES(license_no), address = VALUES(address), driver_since = VALUES(driver_since)
                                    """;

            try (PreparedStatement psCity = connection.prepareStatement(saveCity);
                 PreparedStatement psCustomer = connection.prepareStatement(saveCustomer)) {

                for (Customer customer : customerMap.values()) {

                    psCity.setString(1, customer.getAddress().getCity());
                    psCity.setString(2, customer.getAddress().getZip());
                    psCity.executeUpdate();


                    psCustomer.setString(1, customer.getName());
                    psCustomer.setString(2, customer.getPhoneNo());
                    psCustomer.setString(3, customer.getEmail());
                    psCustomer.setString(4, customer.getLicenseNo());
                    psCustomer.setString(5, customer.getAddress().getAddress());
                    psCustomer.setString(6, customer.getAddress().getZip());
                    psCustomer.setDate(7, java.sql.Date.valueOf(customer.getDriverSince()));
                    psCustomer.addBatch();
                }

                psCustomer.executeBatch();
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Error saving customers: " + e.getMessage(), e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    public void deleteCustomer(Customer c){
        try {
            String query = """
                            DELETE FROM customer WHERE email = ?
                            """;

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, c.getEmail());
            System.out.println("Customer " + c.getName() + " has been deleted.");
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting customer: " + e.getMessage(), e);
        }
    }

}
