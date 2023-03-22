package com.example.rewardsrestful.controller;

import com.example.rewardsrestful.exception.CustomerNotFoundException;
import com.example.rewardsrestful.exception.PointsNotValidException;
import com.example.rewardsrestful.model.CusPoints;
import com.example.rewardsrestful.model.Customer;
import com.example.rewardsrestful.model.ErrorResponse;
import com.example.rewardsrestful.model.ResponseMessage;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/api")
public class CustomerController {

    private CustomerService customerService;
    private TransactionService transactionService;

    @Autowired
    public CustomerController(CustomerService customerService, TransactionService transactionService){
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @RequestMapping(value = "/customers/{id}",method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id){
        Customer customer = customerService.findById(id);
        if(customer == null){
            throw  new CustomerNotFoundException("customer not exist!");
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/customers",method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomers(){
        List<Customer> customers = customerService.findAllCustomer();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //@RequestMapping(value = "/customers",method = RequestMethod.POST)
    @PostMapping("/customers")
    public ResponseEntity<ResponseMessage> createCustomer(@Validated @RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(new ResponseMessage("a customer has been created", savedCustomer), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customers/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @Validated @RequestBody Customer customer){
        Customer currentCustomer = customerService.findById(id);
        if(currentCustomer == null){
            throw new CustomerNotFoundException("customer not exist!");
        }
        currentCustomer.setName(customer.getName());
        currentCustomer.setEmail(customer.getEmail());
        currentCustomer.setPoints(customer.getPoints());
        customerService.updateCustomer(currentCustomer);
        return new ResponseEntity<>(currentCustomer,HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseMessage> deleteCustomer(@PathVariable("id") long id){
        Customer customer = customerService.findById(id);
        if(customer == null){
            throw new CustomerNotFoundException("customer not exist!");
        }
        transactionService.deleteTransactionByCustomer(id);
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(new ResponseMessage("customer has been deleted", customer),HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/monthpoints/total",method = RequestMethod.GET)
    public ResponseEntity<?> getAllMonthPoints(){
        Map<Long,Integer> mymap = transactionService.findTransactionTotal();
        List<CusPoints> cusPointsList = new ArrayList<>();
        List<Customer> customers = customerService.findAllCustomer();
        for(Customer customer: customers){
            Long cusID = customer.getId();
            CusPoints cusPoints = new CusPoints();
            cusPoints.setId(cusID);
            cusPoints.setName(customer.getName());
            cusPoints.setEmail(customer.getEmail());
            if(mymap.containsKey(cusID)){
                cusPoints.setPoints(mymap.get(cusID));
            }
            else{
                cusPoints.setPoints(0);
            }
            cusPointsList.add(cusPoints);
        }
        return new ResponseEntity<>(cusPointsList, HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/permonth/{month}",method = RequestMethod.GET)
    public ResponseEntity<?> getSpecificMonthPoints(@PathVariable("month") int month){
        Map<Long,Integer> mymap = transactionService.findTransactionPer(month);
        List<CusPoints> cusPointsList = new ArrayList<>();
        List<Customer> customers = customerService.findAllCustomer();
        for(Customer customer: customers){
            Long cusID = customer.getId();
            CusPoints cusPoints = new CusPoints();
            cusPoints.setId(cusID);
            cusPoints.setName(customer.getName());
            cusPoints.setEmail(customer.getEmail());
            if(mymap.containsKey(cusID)){
                cusPoints.setPoints(mymap.get(cusID));
            }
            else{
                cusPoints.setPoints(0);
            }
            cusPointsList.add(cusPoints);
        }
        return new ResponseEntity<>(cusPointsList, HttpStatus.OK);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerConsumerNotFound(Exception ex) {
        logger.error("Can't find consumer");
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
