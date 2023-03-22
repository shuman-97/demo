package com.example.rewardsrestful.service;

import com.example.rewardsrestful.dao.CustomerRepository;
import com.example.rewardsrestful.entity.CustomerEntity;
import com.example.rewardsrestful.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.rewardsrestful.util.CustomerEntityVoConverter.convertEntityToVo;
import static com.example.rewardsrestful.util.CustomerEntityVoConverter.convertVoToEntity;


@Service("customerService")
public class CustomerServiceImplement implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer findById(long id){
        System.out.println(id);
        CustomerEntity customerEntity = customerRepository.findById(id).orElse(null);
        return convertEntityToVo(customerEntity);
    }
    @Override
    public List<Customer> findAllCustomer(){
        List<CustomerEntity> customer = customerRepository.findAll();
        return customer.stream().map(e->convertEntityToVo(e)).collect(Collectors.toList());
    }

    @Override
    public Customer saveCustomer(Customer customer){
        CustomerEntity customerEntity = customerRepository.saveAndFlush(convertVoToEntity(customer));
        return convertEntityToVo(customerEntity);
    }

    @Override
    public Customer updateCustomer(Customer customer){
        CustomerEntity customerEntity = customerRepository.saveAndFlush(convertVoToEntity(customer));
        return convertEntityToVo(customerEntity);
    }
    @Override
    public void deleteCustomerById(long id){
        customerRepository.deleteById(id);
    }
}
