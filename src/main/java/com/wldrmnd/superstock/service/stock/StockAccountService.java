package com.wldrmnd.superstock.service.stock;

import com.wldrmnd.superstock.domain.tables.records.StockAccountRecord;
import com.wldrmnd.superstock.jooq.stock.StockAccountJooqRepository;
import com.wldrmnd.superstock.request.stock.account.CreateStockAccountRequest;
import com.wldrmnd.superstock.request.stock.account.FindStockAccountRequest;
import com.wldrmnd.superstock.request.stock.account.UpdateStockAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockAccountService {

    private final StockAccountJooqRepository stockAccountJooqRepository;

    public StockAccountRecord create(CreateStockAccountRequest request) {
        return stockAccountJooqRepository.create(request);
    }

    public List<StockAccountRecord> find(FindStockAccountRequest request) {
        return stockAccountJooqRepository.find(request);
    }

    public StockAccountRecord update(UpdateStockAccountRequest request) {
        return stockAccountJooqRepository.update(request);
    }
}
