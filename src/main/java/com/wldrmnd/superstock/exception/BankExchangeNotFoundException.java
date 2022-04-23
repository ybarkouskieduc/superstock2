package com.wldrmnd.superstock.exception;

import com.wldrmnd.superstock.model.bank.Currency;

public class BankExchangeNotFoundException extends IllegalArgumentException {

    public BankExchangeNotFoundException(
            Long bankId,
            Currency currencyIn,
            Currency currencyOut
    ) {
        super(
                String.format(
                        "Bank [%s] exchange rate is not found for %s -> %s.",
                        bankId,
                        currencyIn,
                        currencyOut
                )
        );
    }
}
