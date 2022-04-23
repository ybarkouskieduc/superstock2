package com.wldrmnd.superstock.request.stock;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ExchangeStockRequest {

    private Long userId;
    private Long stockId;
    private BigDecimal amount;

    // scheduled -> optional
    private BigDecimal desiredPriceInUSD;
}
