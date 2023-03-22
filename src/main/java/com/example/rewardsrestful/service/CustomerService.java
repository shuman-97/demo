package com.example.rewardsrestful.service;

import com.example.rewardsrestful.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer findById(long id);
    List<Customer> findAllCustomer();
    Customer saveCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    void deleteCustomerById(long id);

}
