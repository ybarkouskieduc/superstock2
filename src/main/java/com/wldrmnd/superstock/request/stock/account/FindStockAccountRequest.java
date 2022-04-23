package com.wldrmnd.superstock.request.stock.account;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindStockAccountRequest {

    private Long id;
    private Long userId;
    private Long stockId;
}
