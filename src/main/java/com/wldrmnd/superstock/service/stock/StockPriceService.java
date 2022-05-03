package com.wldrmnd.superstock.service.stock;

import com.wldrmnd.superstock.domain.tables.records.StockPriceRecord;
import com.wldrmnd.superstock.jooq.stock.StockPriceJooqRepository;
import com.wldrmnd.superstock.request.stock.price.FindStockPriceRequest;
import com.wldrmnd.superstock.request.stock.price.CreateStockPriceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockPriceService {


    private final StockPriceJooqRepository stockPriceJooqRepository;

    public StockPriceRecord create(CreateStockPriceRequest request) {
        return stockPriceJooqRepository.create(request);
    }

    public List<StockPriceRecord> find(FindStockPriceRequest request) {
        return stockPriceJooqRepository.find(request);
    }


}
