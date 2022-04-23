package com.wldrmnd.superstock.request.bank.exchange;

import com.wldrmnd.superstock.model.bank.Currency;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CreateBankExchangeTransactionRequest {

    private Long bankId;
    private Long userId;
    private Currency currencyIn;
    private Currency currencyOut;
    private BigDecimal amountIn;
    private BigDecimal amountOut;
    private BigDecimal rate;
    private BigDecimal fee;
}
