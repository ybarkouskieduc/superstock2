package com.wldrmnd.superstock.service.stock;

import com.wldrmnd.superstock.jooq.stock.StockJooqRepository;
import com.wldrmnd.superstock.domain.tables.records.StockRecord;
import com.wldrmnd.superstock.request.stock.FindStockRequest;
import com.wldrmnd.superstock.request.stock.CreateStockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockJooqRepository stockJooqRepository;

    public StockRecord create(CreateStockRequest request) {
        return stockJooqRepository.create(request);
    }

    public List<StockRecord> find(FindStockRequest request) {
        return stockJooqRepository.find(request);
    }

    public List<StockRecord> findAll() {
        return stockJooqRepository.find(FindStockRequest.builder()
                .findAllOnEmptyCriteria(true)
                .build()
        );
    }
}
