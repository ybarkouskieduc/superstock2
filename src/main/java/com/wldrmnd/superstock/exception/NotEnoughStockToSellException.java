package com.wldrmnd.superstock.exception;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class NotEnoughStockToSellException extends IllegalStateException {

    public NotEnoughStockToSellException(
            Long userId,
            Long stockId,
            BigDecimal exceptedStockAmount,
            BigDecimal actualStockAmount
    ) {
        super(
                String.format(
                    "Not enough stocks in the [%d] stock account for user %d. Excepted: %s, available: %s.",
                    stockId, userId,
                    NumberFormat.getCurrencyInstance().format(exceptedStockAmount),
                    NumberFormat.getCurrencyInstance().format(actualStockAmount)
                )
        );
    }
}
