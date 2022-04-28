package com.wldrmnd.superstock.request.stock.price;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindStockPriceRequest {

    private Long id;
    private Long stockId;
    private boolean lastStockPrice;
}
