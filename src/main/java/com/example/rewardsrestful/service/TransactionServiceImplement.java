package com.example.rewardsrestful.service;


import com.example.rewardsrestful.dao.CustomerRepository;
import com.example.rewardsrestful.dao.TransactionRepository;
import com.example.rewardsrestful.entity.TransactionEntity;
import com.example.rewardsrestful.exception.CustomerNotFoundException;
import com.example.rewardsrestful.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.rewardsrestful.util.TransactionEntityVoConverter.convertEntityToVo;
import static com.example.rewardsrestful.util.TransactionEntityVoConverter.convertVoToEntity;

@Service("transactionService")
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    private CustomerRepository customerRepository;
    @Override
    public Transaction findById(long id){
        TransactionEntity transactionEntity = transactionRepository.findById(id).orElse(null);
        return convertEntityToVo(transactionEntity);
    }

    @Override
    public List<Transaction> findAllTransactions(){
        List<TransactionEntity> transaction = transactionRepository.findAll();
        return transaction.stream().map(e-> convertEntityToVo(e)).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> find3MonthTransactions(){
        List<TransactionEntity> transactions = transactionRepository.find3MonthTransaction();
        return transactions.stream().map(e->convertEntityToVo(e)).collect(Collectors.toList());
    }

    //List<Transaction> findTransactionPer();
    public Map<Long,Integer> findTransactionPer(int month){
        System.out.println("this is a importanrt "+month);
        //today
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MILLISECOND, 0);
        //Date firstDateOfCurrentMonth = aCalendar.getTime();
        aCalendar.add(Calendar.MONTH, -month+1);
        Date firstDateOfStartDtae = aCalendar.getTime();
        aCalendar.add(Calendar.MONTH, 1);
        Date nextDateOfEndDate = aCalendar.getTime();
        System.out.println(firstDateOfStartDtae);
        System.out.println(nextDateOfEndDate);
        //LocalDate currenttime = LocalDate.now().withDayOfMonth(1);
        List<TransactionEntity> transactions = transactionRepository.findPerMonthTransaction(firstDateOfStartDtae, nextDateOfEndDate);
        Map<Long,Integer> map = transactions.stream().collect(Collectors.toMap(e->e.getCustomerID(), e->Transaction.transacPoints(e.getAmount()) ,(a,b)->a+b ));
        return map;
    }

    public Map<Long,Integer> findTransactionTotal(){
        List<TransactionEntity> transactions = transactionRepository.find3MonthTransaction();
        Map<Long,Integer> map = transactions.stream().collect(Collectors.toMap(e->e.getCustomerID(), e->Transaction.transacPoints(e.getAmount()) ,(a,b)->a+b ));
        return map;
    }
    @Override
    public Transaction saveTransaction(Transaction transaction){
        TransactionEntity transactionEntity = transactionRepository.saveAndFlush(convertVoToEntity(transaction));
        return convertEntityToVo(transactionEntity);
    }
    @Override
    public Transaction updateTransaction(Transaction transaction){
        TransactionEntity transactionEntity = transactionRepository.saveAndFlush(convertVoToEntity(transaction));
        return convertEntityToVo(transactionEntity);
    }
    @Override
    public void deleteTransactionById(long id){
        transactionRepository.deleteById(id);
    }

    @Override
    public void deleteTransactionByCustomer(long id){
        transactionRepository.deleteTransactionByCustomerID(id);
    }
}
