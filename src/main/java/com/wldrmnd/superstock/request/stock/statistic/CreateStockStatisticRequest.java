package com.wldrmnd.superstock.request.stock.statistic;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CreateStockStatisticRequest {

    private Long stockId;
    private BigDecimal dividend;
    private Long volume;
    private BigDecimal marketValue;
}
