package com.wldrmnd.superstock.request.stock.account;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class UpdateStockAccountRequest {

    private Long id;
    private Long userId;
    private Long stockId;

    private BigDecimal amount;
}
