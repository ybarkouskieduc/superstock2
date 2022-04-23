package com.wldrmnd.superstock.request.stock.transaction;

import com.wldrmnd.superstock.domain.enums.StockTransactionGoal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindStockTransactionRequest {

    private Long id;
    private Long userId;
    private Long stockId;
    private Long stockPriceId;
    private StockTransactionGoal goal;
}
