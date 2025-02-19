package org.example.rental;

import org.example.car.Car;
import org.example.customer.Customer;

import java.time.LocalDate;
import java.util.*;;

public class Rental {

    private Customer customer;
    private Car car;

    private LocalDate startDate;
    private LocalDate endDate;

    private int maxKM;
    private int kmDriven;

    private RentalStatus rentalStatus;

    public Rental() {}

    public Rental(Customer customer, Car car, LocalDate startDate, LocalDate endDate, int maxKM) {
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxKM = maxKM;
        this.rentalStatus = RentalStatus.ACTIVE;
    }

    public void endRental(Rental rental, int kmDriven){
        this.rentalStatus = RentalStatus.COMPLETED;
        this.kmDriven = kmDriven;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getMaxKM() {
        return maxKM;
    }

    public void setMaxKM(int maxKM) {
        this.maxKM = maxKM;
    }

    public int getKmDriven() {
        return kmDriven;
    }

    public void setKmDriven(int kmDriven) {
        this.kmDriven = kmDriven;
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    @Override
    public String toString(){
        return String.format("""
        ----------------------------
        Rental Details
        ----------------------------
        Customer        : %s
        Car             : %s
        Rental date     : %s
        End date        : %s
        Rental status   : %s
        ----------------------------
        """, customer, car, startDate,endDate, rentalStatus);
    }

}
