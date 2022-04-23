package com.wldrmnd.superstock.exception;

import com.wldrmnd.superstock.model.bank.Currency;

public class UserAccountNotFoundException extends IllegalArgumentException {

    public UserAccountNotFoundException(Long userId, Currency currency) {
        super(String.format("%s account for {%d} user is not found.", currency.name(), userId));
    }

    public UserAccountNotFoundException(Long userId, Long stockId) {
        super(String.format("Stock [%s] account for {%d} user is not found.", stockId, userId));
    }
}
