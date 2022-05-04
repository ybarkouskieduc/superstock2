package com.wldrmnd.superstock.request.stock;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
public class CalculateStockProfitRequest {

    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Long stockId;
    private Long userId;
}
