package com.wldrmnd.superstock.jooq.stock;

import com.wldrmnd.superstock.domain.tables.StockAccountStatistic;
import com.wldrmnd.superstock.domain.tables.records.StockAccountStatisticRecord;
import com.wldrmnd.superstock.domain.tables.records.StockTransactionRecord;
import com.wldrmnd.superstock.request.stock.transaction.CreateStockTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.wldrmnd.superstock.domain.Tables.STOCK_ACCOUNT_STATISTIC;
import static com.wldrmnd.superstock.domain.Tables.STOCK_TRANSACTION;

@Component
@RequiredArgsConstructor
public class StockAccountStatisticJooqRepository {

    private final DSLContext dslContext;

    public StockAccountStatisticRecord create(
            Long userId,
            Long stockId,
            BigDecimal profit
    ) {
        StockAccountStatisticRecord stockAccountStatisticRecord = dslContext.newRecord(
                STOCK_ACCOUNT_STATISTIC
        );

        stockAccountStatisticRecord.setUserId(userId);
        stockAccountStatisticRecord.setStockId(stockId);
        stockAccountStatisticRecord.setProfit(profit);
        stockAccountStatisticRecord.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        dslContext.attach(stockAccountStatisticRecord);
        stockAccountStatisticRecord.store();

        return stockAccountStatisticRecord;
    }

    public List<StockAccountStatisticRecord> find(
            Long userId,
            Long stockId,
            LocalDateTime dateFrom,
            LocalDateTime dateTo
    ) {
        return dslContext
                .select()
                .from(STOCK_ACCOUNT_STATISTIC)
                .where(
                        STOCK_ACCOUNT_STATISTIC.USER_ID.eq(userId)
                                .and(STOCK_ACCOUNT_STATISTIC.STOCK_ID.eq(stockId))
                                .and(STOCK_ACCOUNT_STATISTIC.CREATED_AT.lessOrEqual(Timestamp.valueOf(dateTo)))
                                .and(STOCK_ACCOUNT_STATISTIC.CREATED_AT.greaterOrEqual(Timestamp.valueOf(dateFrom)))
                )
                .orderBy(STOCK_ACCOUNT_STATISTIC.CREATED_AT.desc())
                .fetchInto(StockAccountStatisticRecord.class);
    }
}
