package com.wldrmnd.superstock.jooq.bank;

import com.wldrmnd.superstock.domain.tables.records.BankExchangeTransactionRecord;
import com.wldrmnd.superstock.domain.tables.records.BankReviewRecord;
import com.wldrmnd.superstock.request.bank.exchange.FindBankExchangeTransactionRequest;
import com.wldrmnd.superstock.request.bank.review.CreateBankReviewRequest;
import com.wldrmnd.superstock.request.bank.review.FindBankReviewRequest;
import com.wldrmnd.superstock.request.bank.review.UpdateBankReviewRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wldrmnd.superstock.domain.Tables.BANK_EXCHANGE_TRANSACTION;
import static com.wldrmnd.superstock.domain.Tables.BANK_REVIEW;
import static org.jooq.impl.DSL.noCondition;

@Component
@RequiredArgsConstructor
public class BankReviewJooqRepository {
    
    private final DSLContext dslContext;

    public BankReviewRecord create(CreateBankReviewRequest request) {
        BankReviewRecord bankReviewRecord = dslContext.newRecord(BANK_REVIEW);

        // Required
        bankReviewRecord.setBankId(request.getBankId());
        bankReviewRecord.setUserId(request.getUserId());
        bankReviewRecord.setRate(request.getRate());

        // Optional
        if (request.getReview() != null) {
            bankReviewRecord.setReview(request.getReview());
        }

        dslContext.attach(bankReviewRecord);
        bankReviewRecord.store();

        return bankReviewRecord;
    }

    public BankReviewRecord update(UpdateBankReviewRequest request) {
        BankReviewRecord bankReview = find(
                FindBankReviewRequest.builder()
                        .reviewId(request.getReviewId())
                        .build()
        ).stream().findFirst().orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("Bank review [%d] is not found.", request.getReviewId())
                )
        );

        if (!request.getReview().isBlank()) {
            bankReview.setReview(request.getReview());
        }
        if (request.getRate() != null) {
            bankReview.setRate(request.getRate());
        }

        return bankReview;
    }

    public List<BankReviewRecord> find(
            FindBankReviewRequest request
    ) {
        return dslContext.select()
                .from(BANK_REVIEW)
                .where(generateWhereCondition(request))
                .fetchInto(BankReviewRecord.class);
    }


    private Condition generateWhereCondition(
            FindBankReviewRequest request
    ) {
        Condition condition = noCondition();

        if (request.getReviewId() != null) {
            condition = condition.and(BANK_REVIEW.ID.eq(request.getReviewId()));
        }
        if (request.getBankId() != null) {
            condition = condition.and(BANK_REVIEW.BANK_ID.eq(request.getBankId()));
        }
        if (request.getUserId() != null) {
            condition = condition.and(BANK_REVIEW.USER_ID.eq(request.getUserId()));
        }

        if (condition == noCondition()) {
            throw new IllegalArgumentException("Find user request is empty");
        }

        return condition;
    }
}
