package com.wldrmnd.superstock.request.stock.price;

import java.math.BigDecimal;

public class UpdateStockPriceRequest extends CreateStockPriceRequest {

    UpdateStockPriceRequest(Long stockId, BigDecimal price) {
        super(stockId, price);
    }
}