package com.wldrmnd.superstock.jooq.bank;

import com.wldrmnd.superstock.domain.tables.records.BankRecord;
import com.wldrmnd.superstock.request.bank.CreateBankRequest;
import com.wldrmnd.superstock.request.bank.FindBankRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wldrmnd.superstock.domain.Tables.BANK;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.trueCondition;

@Component
@RequiredArgsConstructor
public class BankJooqRepository {

    private final DSLContext dslContext;

    public BankRecord create(CreateBankRequest request) {
        BankRecord bankRecord = dslContext.newRecord(BANK);

        // Required
        bankRecord.setName(request.getName());

        // Optional
        if (request.getDescription() != null) {
            bankRecord.setDescription(request.getDescription());
        }
        if (request.getCountry() != null) {
            bankRecord.setCountry(request.getCountry());
        }

        dslContext.attach(bankRecord);
        bankRecord.store();

        return bankRecord;
    }

    public List<BankRecord> find(FindBankRequest request) {
        return dslContext.select()
                .from(BANK)
                .where(generateWhereCondition(request))
                .fetchInto(BankRecord.class);
    }

    private Condition generateWhereCondition(FindBankRequest request) {
        Condition condition = noCondition();

        if (request.getId() != null) {
            condition = condition.and(BANK.ID.eq(request.getId()));
        }
        if (request.getName() != null) {
            condition = condition.and(BANK.NAME.eq(request.getName()));
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
