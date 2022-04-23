package com.wldrmnd.superstock.jooq.bank;

import com.wldrmnd.superstock.domain.tables.records.BankExchangeRecord;
import com.wldrmnd.superstock.request.bank.exchange.CreateBankExchangeRequest;
import com.wldrmnd.superstock.request.bank.exchange.FindBankExchangeRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wldrmnd.superstock.domain.Tables.BANK_EXCHANGE;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.trueCondition;

@Component
@RequiredArgsConstructor
public class BankExchangeJooqRepository {

    private final DSLContext dslContext;

    public BankExchangeRecord create(CreateBankExchangeRequest request) {
        BankExchangeRecord bankExchangeRecord = dslContext.newRecord(BANK_EXCHANGE);

        bankExchangeRecord.setRate(request.getRate());
        bankExchangeRecord.setBankId(request.getBankId());
        bankExchangeRecord.setCurrencyIn(request.getCurrencyIn().name());
        bankExchangeRecord.setCurrencyOut(request.getCurrencyOut().name());

        if (request.getFee() != null) {
            bankExchangeRecord.setFee(request.getFee());
        }

        dslContext.attach(bankExchangeRecord);
        bankExchangeRecord.store();

        return bankExchangeRecord;
    }

    public List<BankExchangeRecord> find(FindBankExchangeRequest request) {
        SelectConditionStep<Record> selectStep = dslContext.select()
                .from(BANK_EXCHANGE)
                .where(generateWhereCondition(request));

        if (!request.isLastExchangeRate()) {
            return selectStep.fetchInto(BankExchangeRecord.class);
        }

        return selectStep.orderBy(BANK_EXCHANGE.CREATED_AT.desc())
                .limit(1)
                .fetchInto(BankExchangeRecord.class);
    }


    private Condition generateWhereCondition(FindBankExchangeRequest request) {
        Condition condition = noCondition();

        if (request.getId() != null) {
            condition = condition.and(BANK_EXCHANGE.ID.eq(request.getId()));
        }
        if (request.getBankId() != null) {
            condition = condition.and(BANK_EXCHANGE.BANK_ID.eq(request.getBankId()));
        }
        if (request.getCurrencyIn() != null) {
            condition = condition.and(BANK_EXCHANGE.CURRENCY_IN.eq(request.getCurrencyIn().name()));
        }
        if (request.getCurrencyOut() != null) {
            condition = condition.and(BANK_EXCHANGE.CURRENCY_OUT.eq(request.getCurrencyOut().name()));
        }
        if (request.getFee() != null) {
            condition = condition.and(BANK_EXCHANGE.FEE.eq(request.getFee()));
        }
        if (request.getRate() != null) {
            condition = condition.and(BANK_EXCHANGE.RATE.eq(request.getRate()));
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
