package com.wldrmnd.superstock.service.bank;

import com.wldrmnd.superstock.domain.tables.records.AccountRecord;
import com.wldrmnd.superstock.jooq.bank.AccountJooqRepository;
import com.wldrmnd.superstock.request.account.CreateAccountRequest;
import com.wldrmnd.superstock.request.account.FindAccountRequest;
import com.wldrmnd.superstock.request.account.UpdateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountJooqRepository accountJooqRepository;

    public List<AccountRecord> find(FindAccountRequest request) {
        return accountJooqRepository.find(request);
    }

    public AccountRecord create(CreateAccountRequest request) {
        return accountJooqRepository.create(request);
    }

    public AccountRecord update(UpdateAccountRequest request) {
        return accountJooqRepository.update(request);
    }
}
