package com.wldrmnd.superstock.exception;

public class StockPriceIsNotExistsException extends IllegalArgumentException {

    public StockPriceIsNotExistsException(
            Long stockId
    ) {
        super(
                String.format(
                        "Stock [%s] price is not exists.",
                        stockId
                )
        );
    }
}
