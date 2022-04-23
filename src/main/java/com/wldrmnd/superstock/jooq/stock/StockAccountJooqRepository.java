package com.wldrmnd.superstock.jooq.stock;

import com.wldrmnd.superstock.domain.tables.records.AccountRecord;
import com.wldrmnd.superstock.domain.tables.records.StockAccountRecord;
import com.wldrmnd.superstock.request.account.FindAccountRequest;
import com.wldrmnd.superstock.request.account.UpdateAccountRequest;
import com.wldrmnd.superstock.request.stock.account.CreateStockAccountRequest;
import com.wldrmnd.superstock.request.stock.account.FindStockAccountRequest;
import com.wldrmnd.superstock.request.stock.account.UpdateStockAccountRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wldrmnd.superstock.domain.Tables.STOCK_ACCOUNT;
import static org.jooq.impl.DSL.noCondition;

@Component
@RequiredArgsConstructor
public class StockAccountJooqRepository {
    
    private final DSLContext dslContext;

    public StockAccountRecord create(CreateStockAccountRequest request) {
        StockAccountRecord stockAccountRecord = dslContext.newRecord(STOCK_ACCOUNT);

        // Required
        stockAccountRecord.setStockId(request.getStockId());
        stockAccountRecord.setUserId(request.getUserId());

        // Optional
        if (request.getAmount() != null) {
            stockAccountRecord.setAmount(request.getAmount());
        }

        dslContext.attach(stockAccountRecord);
        stockAccountRecord.store();

        return stockAccountRecord;
    }

    public List<StockAccountRecord> find(FindStockAccountRequest request) {
        return dslContext.select()
                .from(STOCK_ACCOUNT)
                .where(generateWhereCondition(request))
                .fetchInto(StockAccountRecord.class);
    }

    private Condition generateWhereCondition(FindStockAccountRequest request) {
        Condition condition = noCondition();

        if (request.getId() != null) {
            condition = condition.and(STOCK_ACCOUNT.ID.eq(request.getId()));
        }
        if (request.getUserId() != null) {
            condition = condition.and(STOCK_ACCOUNT.USER_ID.eq(request.getUserId()));
        }
        if (request.getStockId() != null) {
            condition = condition.and(STOCK_ACCOUNT.STOCK_ID.eq(request.getStockId()));
        }

        if (condition == noCondition()) {
            throw new IllegalArgumentException("Find user request is empty");
        }

        return condition;
    }

    public StockAccountRecord update(UpdateStockAccountRequest request) {
        StockAccountRecord stockAccountRecord = find(
                FindStockAccountRequest.builder()
                        .userId(request.getUserId())
                        .stockId(request.getStockId())
                        .build()
        ).stream().findFirst().orElseThrow(IllegalArgumentException::new);

        if (request.getAmount() != null) {
            stockAccountRecord.setAmount(request.getAmount());
        }
        dslContext.attach(stockAccountRecord);
        stockAccountRecord.store();

        return stockAccountRecord;
    }
}
