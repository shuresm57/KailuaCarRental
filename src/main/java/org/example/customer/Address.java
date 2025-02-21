package org.example.customer;


public class Address {

    private       String streetName;
    private       String      zip;
    private       String      city;


    public Address(String address, String zip, String city){
        this.streetName = address;
        this.zip = zip;
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }


    public String getZip() {
        return zip;
    }


    public String getCity() {
        return city;
    }



}
