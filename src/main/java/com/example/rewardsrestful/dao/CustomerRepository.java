package com.example.rewardsrestful.dao;

import com.example.rewardsrestful.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    //List<CustomerEntity> findAllTransac(int from, int to);
    @Query(value = "delete from Customer t where t.id = :customerID", nativeQuery = true)
    void deleteCustomerByCustomerID(@Param("customerID") long id);
}
