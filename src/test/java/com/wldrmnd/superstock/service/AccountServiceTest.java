package com.wldrmnd.superstock.service;

import com.wldrmnd.superstock.BaseTest;
import com.wldrmnd.superstock.domain.tables.records.AccountRecord;
import com.wldrmnd.superstock.domain.tables.records.UserRecord;
import com.wldrmnd.superstock.model.bank.Currency;
import com.wldrmnd.superstock.request.account.CreateAccountRequest;
import com.wldrmnd.superstock.request.account.FindAccountRequest;
import com.wldrmnd.superstock.service.bank.AccountService;
import com.wldrmnd.superstock.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class AccountServiceTest extends BaseTest {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Test
    void createSecondAccountForSameUser() {
        UserRecord randomUser = createRandomUsers(1).stream()
                .findFirst().get();
        BigDecimal bynAmount = new BigDecimal("10.00");

        accountService.create(CreateAccountRequest.builder()
                .userId(randomUser.getId())
                .amount(bynAmount)
                .currency(Currency.BYN)
                .build()
        );

        List<AccountRecord> accountRecords = accountService.find(FindAccountRequest.builder()
                .userId(randomUser.getId())
                .build());

        assertFalse(accountRecords.isEmpty());
        assertEquals(2, accountRecords.size());

        AccountRecord bynAccountRecord = accountRecords.stream()
                .filter(it -> it.getCurrency().equals("BYN"))
                .toList()
                .stream().findFirst().orElseThrow(AssertionError::new);

        assertEquals(bynAmount, bynAccountRecord.getAmount());
    }
}
