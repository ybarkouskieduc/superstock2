package com.wldrmnd.superstock.jooq.bank;

import com.wldrmnd.superstock.domain.tables.records.BankExchangeTransactionRecord;
import com.wldrmnd.superstock.request.bank.exchange.CreateBankExchangeRequest;
import com.wldrmnd.superstock.request.bank.exchange.CreateBankExchangeTransactionRequest;
import com.wldrmnd.superstock.request.bank.exchange.FindBankExchangeRequest;
import com.wldrmnd.superstock.request.bank.exchange.FindBankExchangeTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wldrmnd.superstock.domain.Tables.BANK_EXCHANGE_TRANSACTION;
import static org.jooq.impl.DSL.noCondition;

@Component
@RequiredArgsConstructor
public class BankExchangeTransactionJooqRepository {

    private final DSLContext dslContext;

    public BankExchangeTransactionRecord create(
            CreateBankExchangeTransactionRequest request
    ) {
        BankExchangeTransactionRecord bankExchangeTransactionRecord = dslContext.newRecord(
                BANK_EXCHANGE_TRANSACTION
        );

        bankExchangeTransactionRecord.setUserId(request.getUserId());
        bankExchangeTransactionRecord.setBankId(request.getBankId());
        bankExchangeTransactionRecord.setRate(request.getRate());
        bankExchangeTransactionRecord.setCurrencyIn(request.getCurrencyIn().name());
        bankExchangeTransactionRecord.setCurrencyOut(request.getCurrencyOut().name());
        bankExchangeTransactionRecord.setAmountIn(request.getAmountIn());
        bankExchangeTransactionRecord.setAmountOut(request.getAmountOut());

        if (request.getFee() != null) {
            bankExchangeTransactionRecord.setFee(request.getFee());
        }

        dslContext.attach(bankExchangeTransactionRecord);
        bankExchangeTransactionRecord.store();

        return bankExchangeTransactionRecord;
    }


    public List<BankExchangeTransactionRecord> find(
            FindBankExchangeTransactionRequest request
    ) {
        return dslContext.select()
                .from(BANK_EXCHANGE_TRANSACTION)
                .where(generateWhereCondition(request))
                .fetchInto(BankExchangeTransactionRecord.class);
    }


    private Condition generateWhereCondition(
            FindBankExchangeTransactionRequest request
    ) {
        Condition condition = noCondition();

        if (request.getId() != null) {
            condition = condition.and(BANK_EXCHANGE_TRANSACTION.ID.eq(request.getId()));
        }
        if (request.getBankId() != null) {
            condition = condition.and(BANK_EXCHANGE_TRANSACTION.BANK_ID.eq(request.getBankId()));
        }
        if (request.getUserId() != null) {
            condition = condition.and(BANK_EXCHANGE_TRANSACTION.USER_ID.eq(request.getUserId()));
        }
        if (request.getCurrencyIn() != null) {
            condition = condition.and(BANK_EXCHANGE_TRANSACTION.CURRENCY_IN.eq(request.getCurrencyIn().name()));
        }
        if (request.getCurrencyOut() != null) {
            condition = condition.and(BANK_EXCHANGE_TRANSACTION.CURRENCY_OUT.eq(request.getCurrencyOut().name()));
        }
        if (request.getFee() != null) {
            condition = condition.and(BANK_EXCHANGE_TRANSACTION.FEE.eq(request.getFee()));
        }
        if (request.getRate() != null) {
            condition = condition.and(BANK_EXCHANGE_TRANSACTION.RATE.eq(request.getRate()));
        }

        if (condition == noCondition()) {
            throw new IllegalArgumentException("Find user request is empty");
        }

        return condition;
    }
}
