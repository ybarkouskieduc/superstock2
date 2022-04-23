package com.wldrmnd.superstock.model.stock;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class StockPrice implements Serializable {

    private Long id;
    private Long stockId;
    private BigDecimal price;
    private Timestamp createdAt;
}
