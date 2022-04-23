package com.wldrmnd.superstock.model.stock;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class StockAccount implements Serializable {

    private Long id;
    private Long stockId;
    private Long userId;
    private BigDecimal amount;
}
