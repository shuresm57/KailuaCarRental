package org.example.customer;

import java.time.LocalDate;
import java.util.Date;


public class Customer {

    private String name;
    private String phoneNo;
    private String email;
    private String licenseNo;

    private Address address;
    private LocalDate driverSince;

    public Customer(){}

    public Customer(String name, Address address, String phoneNo, String email, String licenseNo, LocalDate driverSince){
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
        this.licenseNo = licenseNo;
        this.driverSince = driverSince;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getDriverSince() {
        return driverSince;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setDriverSince(LocalDate driverSince) {
        this.driverSince = driverSince;
    }

    @Override
    public String toString() {
        return String.format("""
    ============================================
                   CUSTOMER DETAILS
    ============================================
    Name               : %s
    Phone Number       : %s
    Email              : %s
    License No.        : %s
    Driver Since       : %s
    ============================================
                   ADDRESS DETAILS
    ============================================
    Address           : %s
    Zip Code          : %s
    City             : %s
    ============================================
    """,
                name,
                phoneNo,
                email,
                licenseNo,
                driverSince,
                address.getAddress(),
                address.getZip(),
                address.getCity());
    }
}
