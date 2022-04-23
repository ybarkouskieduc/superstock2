package com.wldrmnd.superstock.request.account;

import com.wldrmnd.superstock.model.bank.Currency;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class UpdateAccountRequest {

    private Long userId;
    private Currency currency;
    private BigDecimal amount;
    private Boolean isDefault;
}
