package com.example.rewardsrestful.exception;

import com.example.rewardsrestful.model.Transaction;

public class TransactionNotFoundException extends RuntimeException{
    public String errorMessage;

    public TransactionNotFoundException(){
        super();
    }
    public TransactionNotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return errorMessage;
    }
}
