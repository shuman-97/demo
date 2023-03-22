package com.example.rewardsrestful.model;

import com.example.rewardsrestful.entity.CustomerEntity;
import jakarta.persistence.*;

import javax.validation.constraints.*;
import java.util.Date;

public class Transaction {

    private Long id;
    @NotBlank
    private Date date;

    @NotBlank
    @Min(0)
    @Max(100000000)
    private Integer amount;
    @NotNull
    //private Customer customer;
    private Long customerID;

    public Transaction() {
    }

//    public Transaction(Long id, Date date, int amount, Customer customer) {
//        this.id = id;
//        this.date = date;
//        this.amount = amount;
//        this.customer = customer;
//    }


    public Transaction(Long id, Date date, Integer amount, Long customerID) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.customerID = customerID;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }


    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Integer getAmount() {
        return amount;
    }

//    public Customer getCustomer() {
//        return customer;
//    }
    public static int transacPoints(int amount){
        int addPoint = 0;
        int newAmount = amount;
        if(newAmount>=50 &&newAmount<=100){
            addPoint = newAmount-50;
        }
        else if(newAmount>100){
            addPoint = (newAmount-100)*2+50;
        }
        return addPoint;
    }
    public Long getCustomerID() {
        return customerID;
    }
}
