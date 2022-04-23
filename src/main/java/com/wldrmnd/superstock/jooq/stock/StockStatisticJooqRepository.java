package com.wldrmnd.superstock.jooq.stock;

import com.wldrmnd.superstock.domain.tables.records.StockStatisticRecord;
import com.wldrmnd.superstock.request.stock.statistic.FindStockStatisticRequest;
import com.wldrmnd.superstock.request.stock.statistic.CreateStockStatisticRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wldrmnd.superstock.domain.Tables.STOCK_STATISTIC;
import static org.jooq.impl.DSL.noCondition;

@Component
@RequiredArgsConstructor
public class StockStatisticJooqRepository {
    
    private final DSLContext dslContext;

    public StockStatisticRecord create(CreateStockStatisticRequest request) {
        StockStatisticRecord stockStatisticRecord = dslContext.newRecord(STOCK_STATISTIC);

        // Required
        stockStatisticRecord.setStockId(request.getStockId());

        // Optional
        if (request.getDividend() != null) {
            stockStatisticRecord.setDividend(request.getDividend());
        }
        if (request.getMarketValue() != null) {
            stockStatisticRecord.setMarketValue(request.getMarketValue());
        }
        if (request.getVolume() != null) {
            stockStatisticRecord.setVolume(request.getVolume());
        }

        dslContext.attach(stockStatisticRecord);
        stockStatisticRecord.store();

        return stockStatisticRecord;
    }

    public List<StockStatisticRecord> find(FindStockStatisticRequest request) {
        return dslContext.select()
                .from(STOCK_STATISTIC)
                .where(generateWhereCondition(request))
                .fetchInto(StockStatisticRecord.class);
    }

    private Condition generateWhereCondition(FindStockStatisticRequest request) {
        Condition condition = noCondition();

        if (request.getId() != null) {
            condition = condition.and(STOCK_STATISTIC.ID.eq(request.getId()));
        }
        if (request.getStockId() != null) {
            condition = condition.and(STOCK_STATISTIC.STOCK_ID.eq(request.getStockId()));
        }

        if (condition == noCondition()) {
            throw new IllegalArgumentException("Find user request is empty");
        }

        return condition;
    }
}
