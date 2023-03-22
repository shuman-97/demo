package com.example.rewardsrestful.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "amount")
    private int amount;
    @Column(name = "customerID")
    private Long customerID;

    //@OneToOne
   // @JoinColumn(name = "customerID",referencedColumnName = "id")
    //private CustomerEntity customerEntity;

    public TransactionEntity() {
    }

    public TransactionEntity(Long id, Date date, int amount, Long customerID) {
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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }


    public Long getCustomerID() {
        return customerID;
    }
}
