package com.wldrmnd.superstock.service.bank;

import com.wldrmnd.superstock.domain.tables.records.BankExchangeTransactionRecord;
import com.wldrmnd.superstock.jooq.bank.BankExchangeTransactionJooqRepository;
import com.wldrmnd.superstock.request.bank.exchange.CreateBankExchangeTransactionRequest;
import com.wldrmnd.superstock.request.bank.exchange.FindBankExchangeTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankExchangeTransactionService {

    private final BankExchangeTransactionJooqRepository bankExchangeTransactionJooqRepository;

    public List<BankExchangeTransactionRecord> find(FindBankExchangeTransactionRequest request) {
        return bankExchangeTransactionJooqRepository.find(request);
    }

    public BankExchangeTransactionRecord create(CreateBankExchangeTransactionRequest request) {
        return bankExchangeTransactionJooqRepository.create(request);
    }
}
