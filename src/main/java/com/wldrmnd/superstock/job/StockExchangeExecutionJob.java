package com.wldrmnd.superstock.job;

import com.wldrmnd.superstock.domain.tables.records.ExchangeStockRequestRecord;
import com.wldrmnd.superstock.domain.tables.records.StockPriceRecord;
import com.wldrmnd.superstock.jooq.stock.ExchangeStockRequestJooqRepository;
import com.wldrmnd.superstock.request.stock.ExchangeStockRequest;
import com.wldrmnd.superstock.request.stock.price.FindStockPriceRequest;
import com.wldrmnd.superstock.service.UserFlowService;
import com.wldrmnd.superstock.service.stock.StockPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableAsync
@Component
@RequiredArgsConstructor
public class StockExchangeExecutionJob {

    private final StockPriceService stockPriceService;
    private final UserFlowService userFlowService;

    private final ExchangeStockRequestJooqRepository exchangeStockRequestJooqRepository;

    @Async
    @Scheduled(fixedRate = 100000)
    public void scheduledExchangeStockRequestProcessor() {
        List<ExchangeStockRequestRecord> pendingExchangeStockRequests = exchangeStockRequestJooqRepository.findAllPending();

        for (ExchangeStockRequestRecord exchangeStockRequest : pendingExchangeStockRequests) {
            StockPriceRecord stockPriceRecord = stockPriceService.find(FindStockPriceRequest.builder()
                    .stockId(exchangeStockRequest.getStockId())
                    .lastStockPrice(true)
                    .build()
            ).get(0);

            if (stockPriceRecord.getPrice().compareTo(exchangeStockRequest.getDesiredPrice()) <= 0) {
                userFlowService.buyStocks(ExchangeStockRequest.builder()
                        .stockId(exchangeStockRequest.getStockId())
                        .userId(exchangeStockRequest.getUserId())
                        .amount(exchangeStockRequest.getAmount())
                        .build()
                );

                exchangeStockRequestJooqRepository.markExecuted(exchangeStockRequest.getId());
            }
        }
    }
}
