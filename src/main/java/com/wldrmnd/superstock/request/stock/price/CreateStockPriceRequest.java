package com.wldrmnd.superstock.request.stock.price;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CreateStockPriceRequest {

    private Long stockId;
    private BigDecimal price;
}
