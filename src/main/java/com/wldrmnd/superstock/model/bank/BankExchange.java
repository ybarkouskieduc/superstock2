package com.wldrmnd.superstock.model.bank;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BankExchange implements Serializable {

    private Long id;
    private Long bankId;
    private String currencyIn;
    private String currencyOut;
    private BigDecimal rate;
    private BigDecimal fee;
}
