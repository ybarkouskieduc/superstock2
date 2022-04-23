package com.wldrmnd.superstock.service.stock;

import com.wldrmnd.superstock.domain.tables.records.StockPriceRecord;
import com.wldrmnd.superstock.domain.tables.records.StockTransactionRecord;
import com.wldrmnd.superstock.jooq.stock.StockPriceJooqRepository;
import com.wldrmnd.superstock.jooq.stock.StockTransactionJooqRepository;
import com.wldrmnd.superstock.request.stock.price.FindStockPriceRequest;
import com.wldrmnd.superstock.request.stock.transaction.CreateStockTransactionRequest;
import com.wldrmnd.superstock.request.stock.transaction.FindStockTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockTransactionService {

    private final StockTransactionJooqRepository stockTransactionJooqRepository;
    private final StockPriceJooqRepository stockPriceJooqRepository;

    @Transactional
    public StockTransactionRecord create(CreateStockTransactionRequest request) {
        // find the latest price for current stock
        StockPriceRecord lastStockPrice = stockPriceJooqRepository.find(
                FindStockPriceRequest.builder()
                        .stockId(request.getStockId())
                        .lastStockPrice(true)
                        .build()
                ).stream().findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(String.format("No price for stock %d found.", request.getStockId()))
                );

        return stockTransactionJooqRepository.create(request, lastStockPrice.getId());
    }

    public List<StockTransactionRecord> find(FindStockTransactionRequest request) {
        return stockTransactionJooqRepository.find(request);
    }

    public StockTransactionRecord revert(Long stockTransactionId) {
        return stockTransactionJooqRepository.revert(stockTransactionId);
    }
}
