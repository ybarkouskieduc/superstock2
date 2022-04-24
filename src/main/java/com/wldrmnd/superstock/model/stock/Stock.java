package com.wldrmnd.superstock.model.stock;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Stock implements Serializable {

    private Long id;
    private String name;
    private String sign;
    private String description;
    private BigDecimal currentStockPrice;
}
