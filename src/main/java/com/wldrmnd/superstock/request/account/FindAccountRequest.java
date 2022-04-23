package com.wldrmnd.superstock.request.account;

import com.wldrmnd.superstock.model.bank.Currency;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindAccountRequest {

    private Long id;
    private Long userId;
    private Currency currency;
    private boolean findAllOnEmptyCriteria;
}
