package com.wldrmnd.superstock.jooq.stock;

import com.wldrmnd.superstock.domain.tables.records.StockTransactionRecord;
import com.wldrmnd.superstock.exception.TransactionNotFound;
import com.wldrmnd.superstock.request.stock.transaction.CreateStockTransactionRequest;
import com.wldrmnd.superstock.request.stock.transaction.FindStockTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.wldrmnd.superstock.domain.Tables.STOCK_TRANSACTION;
import static org.jooq.impl.DSL.noCondition;

@Component
@RequiredArgsConstructor
public class StockTransactionJooqRepository {
    
    private final DSLContext dslContext;

    public StockTransactionRecord create(
            CreateStockTransactionRequest request,
            Long stockPriceId
    ) {
        StockTransactionRecord stockTransactionRecord = dslContext.newRecord(
                STOCK_TRANSACTION
        );

        stockTransactionRecord.setUserId(request.getUserId());
        stockTransactionRecord.setStockId(request.getStockId());
        stockTransactionRecord.setStockPriceId(stockPriceId);
        stockTransactionRecord.setAmount(request.getAmount());
        stockTransactionRecord.setGoal(request.getGoal());

        dslContext.attach(stockTransactionRecord);
        stockTransactionRecord.store();

        return stockTransactionRecord;
    }


    public List<StockTransactionRecord> find(
            FindStockTransactionRequest request
    ) {
        return dslContext.select()
                .from(STOCK_TRANSACTION)
                .where(generateWhereCondition(request))
                .fetchInto(StockTransactionRecord.class);
    }

    public StockTransactionRecord revert(
            Long stockTransactionId
    ) {
        StockTransactionRecord stockTransaction = find(FindStockTransactionRequest.builder()
                .id(stockTransactionId)
                .build()
        ).stream().findFirst().orElseThrow(() -> new TransactionNotFound(stockTransactionId));

        stockTransaction.setRevertedAt(Timestamp.valueOf(LocalDateTime.now()));
        dslContext.attach(stockTransaction);
        stockTransaction.store();

        return stockTransaction;
    }


    private Condition generateWhereCondition(
            FindStockTransactionRequest request
    ) {
        Condition condition = noCondition();

        if (request.getId() != null) {
            condition = condition.and(STOCK_TRANSACTION.ID.eq(request.getId()));
        }
        if (request.getUserId() != null) {
            condition = condition.and(STOCK_TRANSACTION.USER_ID.eq(request.getUserId()));
        }
        if (request.getStockId() != null) {
            condition = condition.and(STOCK_TRANSACTION.STOCK_ID.eq(request.getStockId()));
        }
        if (request.getStockPriceId() != null) {
            condition = condition.and(STOCK_TRANSACTION.STOCK_PRICE_ID.eq(request.getStockPriceId()));
        }
        if (request.getGoal() != null) {
            condition = condition.and(STOCK_TRANSACTION.GOAL.eq(request.getGoal()));
        }
        if (request.getDateFrom() != null) {
            condition = condition.and(STOCK_TRANSACTION.CREATED_AT.greaterOrEqual(
                    Timestamp.valueOf(request.getDateFrom()))
            );
        }
        if (request.getDateTo() != null) {
            condition = condition.and(STOCK_TRANSACTION.CREATED_AT.lessOrEqual(
                    Timestamp.valueOf(request.getDateTo()))
            );
        }

        if (condition == noCondition()) {
            throw new IllegalArgumentException("Find user request is empty");
        }

        return condition;
    }
}
