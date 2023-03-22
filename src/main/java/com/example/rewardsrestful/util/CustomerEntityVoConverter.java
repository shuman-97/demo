package com.example.rewardsrestful.util;

import com.example.rewardsrestful.entity.CustomerEntity;
import com.example.rewardsrestful.model.Customer;

public class CustomerEntityVoConverter {

    public static Customer convertEntityToVo(CustomerEntity entity){
        if(entity == null){
            return null;
        }

        Customer customer = new Customer();
        customer.setId(entity.getId());
        customer.setName(entity.getName());
        customer.setEmail(entity.getEmail());
        customer.setPoints(entity.getPoints());
        return customer;
    }

    public static CustomerEntity convertVoToEntity(Customer customer){
        if(customer == null){
            return null;
        }
        return new CustomerEntity(customer.getId(), customer.getName(), customer.getEmail(), customer.getPoints());
    }
}
