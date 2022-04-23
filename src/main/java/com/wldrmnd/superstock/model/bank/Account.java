package com.wldrmnd.superstock.model.bank;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Account implements Serializable {

    private Long id;
    private Long userId;
    private String currency;
    private BigDecimal amount;
    private boolean isDefault;
}
