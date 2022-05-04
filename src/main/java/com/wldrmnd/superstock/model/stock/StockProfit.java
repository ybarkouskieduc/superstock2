package com.wldrmnd.superstock.model.stock;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class StockProfit implements Serializable {

    private String stockName;
    private Long stockId;
    private BigDecimal profit;
}
