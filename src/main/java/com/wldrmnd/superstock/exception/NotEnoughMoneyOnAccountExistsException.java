package com.wldrmnd.superstock.exception;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class NotEnoughMoneyOnAccountExistsException extends IllegalStateException {

    public NotEnoughMoneyOnAccountExistsException(
            Long userId,
            String currencyFrom,
            BigDecimal expected,
            BigDecimal actual
    ) {
        super(
                String.format(
                    "Not enough money in the %s account for user %d. Need: %s%s, available: %s%s.",
                    currencyFrom, userId,
                    NumberFormat.getCurrencyInstance().format(expected), currencyFrom,
                    NumberFormat.getCurrencyInstance().format(actual), currencyFrom
                )
        );
    }
}
