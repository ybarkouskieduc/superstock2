package com.wldrmnd.superstock.jooq.bank;

import com.wldrmnd.superstock.domain.tables.records.AccountRecord;
import com.wldrmnd.superstock.domain.tables.records.UserRecord;
import com.wldrmnd.superstock.request.account.CreateAccountRequest;
import com.wldrmnd.superstock.request.account.FindAccountRequest;
import com.wldrmnd.superstock.request.account.UpdateAccountRequest;
import com.wldrmnd.superstock.request.user.FindUserRequest;
import com.wldrmnd.superstock.request.user.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wldrmnd.superstock.domain.tables.Account.ACCOUNT;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.trueCondition;

@Component
@RequiredArgsConstructor
public class AccountJooqRepository {

    private final DSLContext dslContext;

    public AccountRecord create(CreateAccountRequest request) {
        AccountRecord accountRecord = dslContext.newRecord(ACCOUNT);

        // Required
        accountRecord.setUserId(request.getUserId());

        // Optional
        if (request.getAmount() != null) {
            accountRecord.setAmount(request.getAmount());
        }
        if (request.getIsDefault() != null) {
            accountRecord.setDefault(request.getIsDefault());
        }
        if (request.getCurrency() != null) {
            accountRecord.setCurrency(request.getCurrency().name());
        }

        dslContext.attach(accountRecord);
        accountRecord.store();

        return accountRecord;
    }

    public AccountRecord update(UpdateAccountRequest request) {
        AccountRecord accountRecord = find(
                FindAccountRequest.builder()
                        .userId(request.getUserId())
                        .currency(request.getCurrency())
                        .build()
        ).stream().findFirst().orElseThrow(IllegalArgumentException::new);

        if (request.getAmount() != null) {
            accountRecord.setAmount(request.getAmount());
        }
        if (request.getIsDefault() != null) {
            accountRecord.setDefault(request.getIsDefault());
        }

        dslContext.attach(accountRecord);
        accountRecord.store();

        return accountRecord;
    }


    public List<AccountRecord> find(FindAccountRequest request) {
        return dslContext.select()
                .from(ACCOUNT)
                .where(generateWhereCondition(request))
                .fetchInto(AccountRecord.class);
    }


    private Condition generateWhereCondition(FindAccountRequest request) {
        Condition condition = noCondition();

        if (request.getId() != null) {
            condition = condition.and(ACCOUNT.ID.eq(request.getId()));
        }
        if (request.getCurrency() != null) {
            condition = condition.and(ACCOUNT.CURRENCY.eq(request.getCurrency().name()));
        }
        if (request.getUserId() != null) {
            condition = condition.and(ACCOUNT.USER_ID.eq(request.getUserId()));
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
