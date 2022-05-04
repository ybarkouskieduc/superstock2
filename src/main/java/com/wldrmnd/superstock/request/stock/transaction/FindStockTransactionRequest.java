package com.wldrmnd.superstock.request.stock.transaction;

import com.wldrmnd.superstock.domain.enums.StockTransactionGoal;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
public class FindStockTransactionRequest {

    private Long id;
    private Long userId;
    private Long stockId;
    private Long stockPriceId;
    private StockTransactionGoal goal;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}
