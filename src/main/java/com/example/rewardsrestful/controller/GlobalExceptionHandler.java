package com.example.rewardsrestful.controller;

import com.example.rewardsrestful.exception.PointsNotValidException;
import com.example.rewardsrestful.exception.PurchaseAmountNotValidException;
import com.example.rewardsrestful.model.ErrorResponse;
import com.example.rewardsrestful.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = (Logger) LoggerFactory.getLogger(CustomerController.class);
    @ExceptionHandler(PointsNotValidException.class)
    public ResponseEntity<ErrorResponse> expceptionHandlerPointsNotValid(Exception ex){
        logger.error("points are not valid, it should be equal to or larger than 0");
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    private static Logger logger2 = (Logger) LoggerFactory.getLogger(TransactionController.class);
    @ExceptionHandler(PurchaseAmountNotValidException.class)
    public ResponseEntity<ErrorResponse> expceptionHandlerPurchaseAmountNotValid(Exception ex){
        logger2.error("purchase amount are not valid, it should be equal to or larger than 0");
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
