package com.wldrmnd.superstock.request.stock.transaction;

import com.wldrmnd.superstock.domain.enums.StockTransactionGoal;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CreateStockTransactionRequest {

    private Long userId;
    private Long stockId;
    private BigDecimal amount;
    private StockTransactionGoal goal;
}
