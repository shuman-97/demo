package com.example.rewardsrestful.util;

import com.example.rewardsrestful.entity.TransactionEntity;
import com.example.rewardsrestful.model.Transaction;

public class TransactionEntityVoConverter {

    public static Transaction convertEntityToVo(TransactionEntity entity){
        if(entity == null){
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setId(entity.getId());
        transaction.setAmount(entity.getAmount());
        transaction.setDate(entity.getDate());
        //transaction.setCustomerID(entity.getCustomerEntity().getId());
        transaction.setCustomerID(entity.getCustomerID());
        return transaction;
    }

    public static TransactionEntity convertVoToEntity(Transaction transaction){
        if(transaction == null){
            return null;
        }

        return new TransactionEntity(transaction.getId(),transaction.getDate(),transaction.getAmount(), transaction.getCustomerID());
    }
}
