package com.wldrmnd.superstock.service.bank;

import com.wldrmnd.superstock.domain.tables.records.BankExchangeRecord;
import com.wldrmnd.superstock.jooq.bank.BankExchangeJooqRepository;
import com.wldrmnd.superstock.request.bank.exchange.CreateBankExchangeRequest;
import com.wldrmnd.superstock.request.bank.exchange.FindBankExchangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankExchangeService {

    private final BankExchangeJooqRepository bankExchangeJooqRepository;

    public List<BankExchangeRecord> find(FindBankExchangeRequest request) {
        return bankExchangeJooqRepository.find(request);
    }

    public BankExchangeRecord create(CreateBankExchangeRequest request) {
        return bankExchangeJooqRepository.create(request);
    }
}
