package com.wldrmnd.superstock.request.bank.exchange;

import com.wldrmnd.superstock.model.bank.Currency;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class FindBankExchangeRequest {

    private Long id;
    private Long bankId;
    private Currency currencyIn;
    private Currency currencyOut;
    private BigDecimal rate;
    private BigDecimal fee;
    private boolean lastExchangeRate;
    private boolean findAllOnEmptyCriteria;
}
