package com.wldrmnd.superstock.jooq.stock;

import com.wldrmnd.superstock.domain.tables.records.AccountRecord;
import com.wldrmnd.superstock.domain.tables.records.ExchangeStockRequestRecord;
import com.wldrmnd.superstock.domain.tables.records.StockAccountRecord;
import com.wldrmnd.superstock.request.account.FindAccountRequest;
import com.wldrmnd.superstock.request.account.UpdateAccountRequest;
import com.wldrmnd.superstock.request.stock.ExchangeStockRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.wldrmnd.superstock.domain.Tables.EXCHANGE_STOCK_REQUEST;
import static com.wldrmnd.superstock.domain.Tables.STOCK_ACCOUNT;
import static org.jooq.impl.DSL.noCondition;

@Configuration
@RequiredArgsConstructor
public class ExchangeStockRequestJooqRepository {

    private final DSLContext dslContext;

    public ExchangeStockRequestRecord create(ExchangeStockRequest request) {
        ExchangeStockRequestRecord exchangeStockRequestRecord = dslContext.newRecord(EXCHANGE_STOCK_REQUEST);

        // Required
        exchangeStockRequestRecord.setStockId(request.getStockId());
        exchangeStockRequestRecord.setUserId(request.getUserId());
        exchangeStockRequestRecord.setAmount(request.getAmount());
        exchangeStockRequestRecord.setDesiredPrice(request.getDesiredPriceInUSD());

        dslContext.attach(exchangeStockRequestRecord);
        exchangeStockRequestRecord.store();

        return exchangeStockRequestRecord;
    }

    public ExchangeStockRequestRecord findById(Long exchangeStockRequestId) {
        return dslContext.select()
                .from(EXCHANGE_STOCK_REQUEST)
                .where(EXCHANGE_STOCK_REQUEST.ID.eq(exchangeStockRequestId))
                .fetchInto(ExchangeStockRequestRecord.class)
                .stream().findFirst().get();
    }

    public List<ExchangeStockRequestRecord> findAllPending() {
        return dslContext.select()
                .from(EXCHANGE_STOCK_REQUEST)
                .where(EXCHANGE_STOCK_REQUEST.EXECUTED_AT.isNull())
                .fetchInto(ExchangeStockRequestRecord.class);
    }

    public List<ExchangeStockRequestRecord> findAllPending(Long userId) {
        return dslContext.select()
                .from(EXCHANGE_STOCK_REQUEST)
                .where(EXCHANGE_STOCK_REQUEST.EXECUTED_AT.isNull())
                .and(EXCHANGE_STOCK_REQUEST.USER_ID.eq(userId))
                .fetchInto(ExchangeStockRequestRecord.class);
    }

    public List<ExchangeStockRequestRecord> findAllComplete(Long userId) {
        return dslContext.select()
                .from(EXCHANGE_STOCK_REQUEST)
                .where(EXCHANGE_STOCK_REQUEST.EXECUTED_AT.isNotNull())
                .and(EXCHANGE_STOCK_REQUEST.USER_ID.eq(userId))
                .fetchInto(ExchangeStockRequestRecord.class);
    }

    public ExchangeStockRequestRecord markExecuted(Long exchangeStockRequestId) {
        ExchangeStockRequestRecord exchangeStockRequestRecord = findById(exchangeStockRequestId);

        exchangeStockRequestRecord.setExecutedAt(Timestamp.valueOf(LocalDateTime.now()));

        dslContext.attach(exchangeStockRequestRecord);
        exchangeStockRequestRecord.store();

        return exchangeStockRequestRecord;
    }
}
