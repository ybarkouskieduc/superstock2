package com.wldrmnd.superstock.jooq.stock;

import com.wldrmnd.superstock.domain.tables.records.StockAccountRecord;
import com.wldrmnd.superstock.domain.tables.records.StockPriceRecord;
import com.wldrmnd.superstock.exception.StockPriceIsNotExistsException;
import com.wldrmnd.superstock.request.stock.account.FindStockAccountRequest;
import com.wldrmnd.superstock.request.stock.account.UpdateStockAccountRequest;
import com.wldrmnd.superstock.request.stock.price.FindStockPriceRequest;
import com.wldrmnd.superstock.request.stock.price.CreateStockPriceRequest;
import com.wldrmnd.superstock.request.stock.price.UpdateStockPriceRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wldrmnd.superstock.domain.Tables.STOCK_PRICE;
import static org.jooq.impl.DSL.noCondition;

@Component
@RequiredArgsConstructor
public class StockPriceJooqRepository {

    private final DSLContext dslContext;

    public StockPriceRecord create(CreateStockPriceRequest request) {
        StockPriceRecord stockPriceRecord = dslContext.newRecord(STOCK_PRICE);

        stockPriceRecord.setStockId(request.getStockId());
        stockPriceRecord.setPrice(request.getPrice());

        dslContext.attach(stockPriceRecord);
        stockPriceRecord.store();

        return stockPriceRecord;
    }


    public List<StockPriceRecord> find(FindStockPriceRequest request) {
        SelectConditionStep<Record> selectStep = dslContext.select()
                .from(STOCK_PRICE)
                .where(generateWhereCondition(request));

        if (!request.isLastStockPrice()) {
            return selectStep.fetchInto(StockPriceRecord.class);
        }

        return selectStep.orderBy(STOCK_PRICE.ID.desc())
                .limit(1)
                .fetchInto(StockPriceRecord.class);
    }


    private Condition generateWhereCondition(FindStockPriceRequest request) {
        Condition condition = noCondition();

        if (request.getId() != null) {
            condition = condition.and(STOCK_PRICE.ID.eq(request.getId()));
        }

        if (request.getStockId() != null) {
            condition = condition.and(STOCK_PRICE.STOCK_ID.eq(request.getStockId()));
        }

        if (condition == noCondition()) {
            throw new IllegalArgumentException("Find user request is empty");
        }

        return condition;
    }
}