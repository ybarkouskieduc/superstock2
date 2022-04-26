package com.wldrmnd.superstock.model.stock;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ExchangeStockRequestModel implements Serializable {

    private Long id;
    private Long userId;
    private Long stockId;
    private BigDecimal amount;
    private BigDecimal desiredPrice;
    private Timestamp createdAt;
    private Timestamp executedAt;
}
