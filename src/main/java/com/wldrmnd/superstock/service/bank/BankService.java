package com.wldrmnd.superstock.service.bank;

import com.wldrmnd.superstock.domain.tables.records.BankRecord;
import com.wldrmnd.superstock.jooq.bank.BankJooqRepository;
import com.wldrmnd.superstock.request.bank.CreateBankRequest;
import com.wldrmnd.superstock.request.bank.FindBankRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankJooqRepository bankJooqRepository;

    public List<BankRecord> find(FindBankRequest request) {
        return bankJooqRepository.find(request);
    }

    public BankRecord create(CreateBankRequest request) {
        return bankJooqRepository.create(request);
    }
}
