package org.example.customer;

import java.util.*;

public class Address {

    private String address;
    private String zip;
    private String city;

    private Scanner scanner = new Scanner(System.in);

    public Address(String address, String zip, String city){
        this.address = address;
        this.zip = zip;
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString(){
        return String.format("""
        ----------------------------
        Address Details
        ----------------------------
        Address  : %s
        Zip Code : %s
        City     : %s
        ----------------------------
        """, address, zip, city);
    }
}
