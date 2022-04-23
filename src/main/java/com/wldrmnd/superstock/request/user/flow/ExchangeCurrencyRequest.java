package com.wldrmnd.superstock.request.user.flow;

import com.wldrmnd.superstock.model.bank.Currency;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ExchangeCurrencyRequest {

    private Long userId;
    private Long bankId;
    private Currency currencyIn;
    private Currency currencyOut;
    private BigDecimal amount;
}
