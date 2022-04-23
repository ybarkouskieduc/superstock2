package com.wldrmnd.superstock.jooq.stock;

import com.wldrmnd.superstock.domain.tables.records.StockRecord;
import com.wldrmnd.superstock.request.stock.FindStockRequest;
import com.wldrmnd.superstock.request.stock.CreateStockRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wldrmnd.superstock.domain.Tables.STOCK;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.trueCondition;

@Component
@RequiredArgsConstructor
public class StockJooqRepository {

    private final DSLContext dslContext;

    public StockRecord create(CreateStockRequest request) {
        StockRecord stockRecord = dslContext.newRecord(STOCK);

        // Required
        stockRecord.setName(request.getName());
        stockRecord.setSign(request.getSign());

        // Optional
        if (request.getDescription() != null) {
            stockRecord.setDescription(request.getDescription());
        }

        dslContext.attach(stockRecord);
        stockRecord.store();

        return stockRecord;
    }

    public List<StockRecord> find(FindStockRequest request) {
        return dslContext.select()
                .from(STOCK)
                .where(generateWhereCondition(request))
                .fetchInto(StockRecord.class);
    }

    private Condition generateWhereCondition(FindStockRequest request) {
        Condition condition = noCondition();

        if (request.getId() != null) {
            condition = condition.and(STOCK.ID.eq(request.getId()));
        }
        if (request.getName() != null) {
            condition = condition.and(STOCK.NAME.eq(request.getName()));
        }
        if (request.getSign() != null) {
            condition = condition.and(STOCK.SIGN.eq(request.getSign()));
        }
        if (request.isFindAllOnEmptyCriteria()) {
            condition = trueCondition();
        }

        if (condition == noCondition()) {
            throw new IllegalArgumentException("Find user request is empty");
        }

        return condition;
    }
}
