package com.example.rewardsrestful;

import com.example.rewardsrestful.dao.CustomerRepository;
import com.example.rewardsrestful.dao.TransactionRepository;
import com.example.rewardsrestful.entity.CustomerEntity;
import com.example.rewardsrestful.entity.TransactionEntity;
import com.example.rewardsrestful.model.Customer;
import com.example.rewardsrestful.model.Transaction;
import com.example.rewardsrestful.util.CustomerEntityVoConverter;
import com.example.rewardsrestful.util.TransactionEntityVoConverter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class MyUnitTest {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Order(1)
    @Test
    public void testCustomerCreate(){
        Customer customer  = new Customer();
        customer.setId(1L);
        customer.setName("Alex");
        customer.setEmail("alex@gmail.com");
        customer.setPoints(0);
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(customer));
        assertNotNull(customerRepository.findById(1L).get());

    }

    @Order(2)
    @Test
    public void testCustomerReadAll(){
        Customer customer  = new Customer();
        customer.setId(1L);
        customer.setName("Alex");
        customer.setEmail("alex@gmail.com");
        customer.setPoints(0);
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(customer));

        Customer customer2  = new Customer();
        customer2.setId(2L);
        customer2.setName("Bob");
        customer2.setEmail("bob@gmail.com");
        customer2.setPoints(0);
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(customer2));
        assertEquals(2,customerRepository.findAll().size());

    }

    @Order(3)
    @Test
    public void testSingleCustomer(){
        Customer customer  = new Customer();
        customer.setId(1L);
        customer.setName("Alex");
        customer.setEmail("alex@gmail.com");
        customer.setPoints(0);
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(customer));
        assertEquals("Alex",  customerRepository.findById(1L).get().getName());

    }

    @Order(4)
    @Test
    public void testCustomerUpdate() throws InterruptedException {

        Customer customer  = new Customer(1L,"Alex","alex@gmail.com",0);
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(customer));
        System.out.println(customerRepository.findAll().size());
        CustomerEntity customerEntity = customerRepository.findById(1L).get();
        Customer oldCustomer = CustomerEntityVoConverter.convertEntityToVo(customerEntity);
        oldCustomer.setName("NewAlex");
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(oldCustomer));
        assertNotEquals("Alex",  customerRepository.findById(1L).get().getName());
    }

    @Order(5)
    @Test
    public void testCustomerDelete(){
        Customer customer  = new Customer();
        customer.setId(1L);
        customer.setName("Alex");
        customer.setEmail("alex@gmail.com");
        customer.setPoints(0);
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(customer));
        customerRepository.deleteById(1L);
        assertThat(customerRepository.existsById(1L)).isFalse();
    }

    @Order(6)
    @Test
    public void testTransactionCreate() throws ParseException {
        Customer customer  = new Customer(1L,"Alex","alex@gmail.com",0);
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(customer));
        String dateString = "2023-02-03";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = formatter.parse(dateString);
        Transaction transaction  = new Transaction(1L,myDate,120,1L);
        transactionRepository.save(TransactionEntityVoConverter.convertVoToEntity(transaction));
        assertNotNull(transactionRepository.findById(1L).get());

    }

    @Order(7)
    @Test
    public void testTransactionReadAll() throws ParseException {
        Customer customer  = new Customer(1L,"Alex","alex@gmail.com",0);
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(customer));

        String dateString = "2023-02-03";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = formatter.parse(dateString);
        Transaction transaction  = new Transaction(1L,myDate,120,1L);
        transactionRepository.save(TransactionEntityVoConverter.convertVoToEntity(transaction));

        String dateString2 = "2023-03-03";
        Date myDate2 = formatter.parse(dateString2);
        Transaction transaction2  = new Transaction(2L,myDate2,120,1L);
        transactionRepository.save(TransactionEntityVoConverter.convertVoToEntity(transaction2));

        assertEquals(2,transactionRepository.findAll().size());

    }

    @Order(8)
    @Test
    public void testSingleTransaction() throws ParseException {
        Customer customer  = new Customer(1L,"Alex","alex@gmail.com",0);
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(customer));

        String dateString = "2023-02-03";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = formatter.parse(dateString);
        Transaction transaction  = new Transaction(1L,myDate,120,1L);
        transactionRepository.save(TransactionEntityVoConverter.convertVoToEntity(transaction));

        assertEquals(120,  transactionRepository.findById(1L).get().getAmount());

    }

    @Order(9)
    @Test
    public void testTransactionUpdate() throws InterruptedException, ParseException {

        Customer customer  = new Customer(1L,"Alex","alex@gmail.com",0);
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(customer));

        String dateString = "2023-02-03";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = formatter.parse(dateString);
        Transaction transaction  = new Transaction(1L,myDate,120,1L);
        transactionRepository.save(TransactionEntityVoConverter.convertVoToEntity(transaction));

        TransactionEntity transactionEntity = transactionRepository.findById(1L).get();
        Transaction oldTransaction= TransactionEntityVoConverter.convertEntityToVo(transactionEntity);
        oldTransaction.setAmount(150);
        transactionRepository.save(TransactionEntityVoConverter.convertVoToEntity(oldTransaction));
        assertNotEquals(120,  transactionRepository.findById(1L).get().getAmount());
    }

    @Order(10)
    @Test
    public void testTransactionDelete() throws ParseException {
        Customer customer  = new Customer(1L,"Alex","alex@gmail.com",0);
        customerRepository.save(CustomerEntityVoConverter.convertVoToEntity(customer));

        String dateString = "2023-02-03";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = formatter.parse(dateString);
        Transaction transaction  = new Transaction(1L,myDate,120,1L);
        transactionRepository.save(TransactionEntityVoConverter.convertVoToEntity(transaction));

        transactionRepository.deleteById(1L);
        assertThat(transactionRepository.existsById(1L)).isFalse();
    }
}
