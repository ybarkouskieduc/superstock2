package com.wldrmnd.superstock.exception;

public class TransactionNotFound extends IllegalArgumentException {

    public TransactionNotFound(Long transactionId) {
        super(String.format("Transaction [%d] is not found.", transactionId));
    }
}
