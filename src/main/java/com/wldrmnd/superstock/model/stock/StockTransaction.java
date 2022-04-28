package com.wldrmnd.superstock.model.stock;

import com.wldrmnd.superstock.domain.enums.StockTransactionGoal;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class StockTransaction implements Serializable {

    private Long id;
    private Long stockId;
    private Long userId;
    private Long stockPriceId;
    private BigDecimal stockPrice;
    private BigDecimal amount;
    private Timestamp createdAt;
    private Timestamp revertedAt;
    private StockTransactionGoal goal;
}
