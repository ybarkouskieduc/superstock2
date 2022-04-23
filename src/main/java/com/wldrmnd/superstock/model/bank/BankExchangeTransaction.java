package com.wldrmnd.superstock.model.bank;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class BankExchangeTransaction implements Serializable {

    private Long id;
    private Long userId;
    private Long bankId;
    private String currencyIn;
    private String currencyOut;
    private BigDecimal amountIn;
    private BigDecimal amountOut;
    private BigDecimal rate;
    private BigDecimal fee;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
