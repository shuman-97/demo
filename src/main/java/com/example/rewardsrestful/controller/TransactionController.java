package com.example.rewardsrestful.controller;

import com.example.rewardsrestful.dao.TransactionRepository;
import com.example.rewardsrestful.exception.CustomerNotFoundException;
import com.example.rewardsrestful.exception.TransactionNotFoundException;
import com.example.rewardsrestful.model.Customer;
import com.example.rewardsrestful.model.ErrorResponse;
import com.example.rewardsrestful.model.ResponseMessage;
import com.example.rewardsrestful.model.Transaction;
import com.example.rewardsrestful.service.CustomerService;
import com.example.rewardsrestful.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api")
public class TransactionController {

    private TransactionService transactionService;
    private CustomerService customerService;

    @Autowired
    public TransactionController(TransactionService transactionService, CustomerService customerService){
        this.transactionService = transactionService;
        this.customerService = customerService;
    }

    private static Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @RequestMapping(value = "/transactions/{id}",method = RequestMethod.GET)
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") long id){
        Transaction transaction = transactionService.findById(id);
        if(transaction == null){
            throw new TransactionNotFoundException("transaction not exist!");
        }
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @RequestMapping(value = "/transactions",method = RequestMethod.GET)
    public ResponseEntity<?> getAllTransactions(){
        List<Transaction> transactions = transactionService.findAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @RequestMapping(value = "/transactions/3month",method = RequestMethod.GET)
    public ResponseEntity<?> get3MonthTransactions(){
        List<Transaction> transactions = transactionService.find3MonthTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @RequestMapping(value = "/transactions",method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> createTransaction(@Validated @RequestBody Transaction transaction){
        if(customerService.findById(transaction.getCustomerID())==null){
            throw new CustomerNotFoundException("customer not exist!");
        }
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        //at same time update points
        Customer customer = customerService.findById(transaction.getCustomerID());
        int addPoint = calculatePoints(transaction.getAmount());
        customer.setPoints(customer.getPoints()+addPoint);
        customerService.updateCustomer(customer);
        return new ResponseEntity<>(new ResponseMessage("a transaction has been created", savedTransaction), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/transactions/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Transaction> updateTransaction(@PathVariable("id") long id, @Validated @RequestBody Transaction transaction){
        Transaction currentTransaction = transactionService.findById(id);
        if(currentTransaction == null){
            throw new TransactionNotFoundException("transaction not exist!");
        }
        if(customerService.findById(transaction.getCustomerID())==null){
            throw new CustomerNotFoundException("updated customer not exist!");
        }
        int oriAmount = currentTransaction.getAmount();
        long oriCustomerID = currentTransaction.getCustomerID();
        int oldPoints = calculatePoints(oriAmount);
        int newPoints = calculatePoints(transaction.getAmount());
        Customer oldcustomer = customerService.findById(oriCustomerID);
        if(oriCustomerID == transaction.getCustomerID()){
            //only change old customer points
            oldcustomer.setPoints(oldcustomer.getPoints()-oldPoints+newPoints);
            customerService.updateCustomer(oldcustomer);
        }
        else{
            //need to change original customer points and new customer points
            oldcustomer.setPoints(oldcustomer.getPoints()-oldPoints);
            customerService.updateCustomer(oldcustomer);
            Customer newcustomer = customerService.findById(transaction.getCustomerID());
            newcustomer.setPoints(newcustomer.getPoints()+newPoints);
            customerService.updateCustomer(newcustomer);
        }
        currentTransaction.setDate(transaction.getDate());
        currentTransaction.setAmount(transaction.getAmount());
        currentTransaction.setCustomerID(transaction.getCustomerID());
        transactionService.updateTransaction(currentTransaction);
        return new ResponseEntity<>(currentTransaction,HttpStatus.OK);
    }

    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseMessage> deleteTransaction(@PathVariable("id") long id){
        Transaction transaction = transactionService.findById(id);
        if(transaction == null){
            throw new CustomerNotFoundException("transaction not exist!");
        }
        //update customer
        Customer customer = customerService.findById(transaction.getCustomerID());
        int corrPoints = calculatePoints(transaction.getAmount());
        customer.setPoints(customer.getPoints() - corrPoints);
        customerService.updateCustomer(customer);
        transactionService.deleteTransactionById(id);
        return new ResponseEntity<>(new ResponseMessage("transaction has been deleted", transaction),HttpStatus.OK);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerTransactionNotFound(Exception ex) {
        logger.error("Can't find transaction");
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    public int calculatePoints(int amount){
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
}
