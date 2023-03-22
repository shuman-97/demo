package com.example.rewardsrestful.dao;

import com.example.rewardsrestful.entity.TransactionEntity;
import com.example.rewardsrestful.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Transactional
    @Modifying
    @Query(value ="delete from Transaction t where t.customerID = :customerID", nativeQuery = true)
    void deleteTransactionByCustomerID(@Param("customerID") long id);

//    @Modifying
//    @Query("select * from Transaction")
//    List <TransactionEntity> find3MonthTransaction();

    @Transactional
    @Modifying
    @Query(value ="select * from Transaction t where t.date between LOCALTIMESTAMP -  '3' MONTH and LOCALTIMESTAMP", nativeQuery = true)
    List<TransactionEntity> find3MonthTransaction();

    @Transactional
    @Modifying
    @Query(value ="select * from Transaction t where t.date >= :startDate and t.date< :endDate", nativeQuery = true)
    List<TransactionEntity> findPerMonthTransaction(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
