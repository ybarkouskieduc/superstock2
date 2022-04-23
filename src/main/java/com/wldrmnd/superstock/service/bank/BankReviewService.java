package com.wldrmnd.superstock.service.bank;

import com.wldrmnd.superstock.domain.tables.records.BankReviewRecord;
import com.wldrmnd.superstock.jooq.bank.BankReviewJooqRepository;
import com.wldrmnd.superstock.request.bank.review.CreateBankReviewRequest;
import com.wldrmnd.superstock.request.bank.review.FindBankReviewRequest;
import com.wldrmnd.superstock.request.bank.review.UpdateBankReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankReviewService {

    private final BankReviewJooqRepository bankReviewJooqRepository;

    public BankReviewRecord create(CreateBankReviewRequest request) {
        return bankReviewJooqRepository.create(request);
    }

    public BankReviewRecord update(UpdateBankReviewRequest request) {
        return bankReviewJooqRepository.update(request);
    }

    public List<BankReviewRecord> find(FindBankReviewRequest request) {
        return bankReviewJooqRepository.find(request);
    }
}
