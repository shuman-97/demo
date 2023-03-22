package com.example.rewardsrestful.service;

import com.example.rewardsrestful.model.Customer;
import com.example.rewardsrestful.model.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {

    Transaction findById(long id);
    List<Transaction> findAllTransactions();
    List<Transaction> find3MonthTransactions();

    //get the total points of n month ago(1 means current month, 2 means last month) transaction Local time
    //Map key: customer id, value: points
    Map<Long,Integer> findTransactionPer(int month);

    //get the total points of last 3 month(like December 21-March 21) transaction Local time
    //Map key: customer id, value: points
    Map<Long,Integer> findTransactionTotal();
    Transaction saveTransaction(Transaction transaction);
    Transaction updateTransaction(Transaction transaction);
    void deleteTransactionById(long id);
    void deleteTransactionByCustomer(long id);
}
