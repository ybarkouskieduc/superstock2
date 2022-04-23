package com.wldrmnd.superstock.service;

import com.oblac.nomen.Nomen;
import com.wldrmnd.superstock.domain.tables.records.AccountRecord;
import com.wldrmnd.superstock.domain.tables.records.UserRecord;
import com.wldrmnd.superstock.model.bank.Currency;
import com.wldrmnd.superstock.request.user.CreateUserRequest;
import com.wldrmnd.superstock.request.account.FindAccountRequest;
import com.wldrmnd.superstock.service.bank.AccountService;
import com.wldrmnd.superstock.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Test
    void createUserAndAccount() {
        String username = Nomen.randomName();
        String password = Nomen.randomName(10);
        UserRecord userRecord = userService.createUserAndDefaultAccount(CreateUserRequest.builder()
                .username(username)
                .password(password)
                .build()
        );

        AccountRecord accountRecord = accountService.find(FindAccountRequest.builder()
                .userId(userRecord.getId())
                .currency(Currency.USD)
                .build()
        ).stream().findFirst().orElse(null);

        assertNotNull(userRecord);
        assertNotNull(userRecord.getId());
        assertEquals(username, userRecord.getUsername());
        assertEquals(password, userRecord.getPassword());

        assertNotNull(accountRecord);
        assertEquals(accountRecord.getUserId(), userRecord.getId());
        assertEquals(BigDecimal.valueOf(0L, 2), accountRecord.getAmount());
        assertEquals(Currency.USD.name(), accountRecord.getCurrency());
        assertTrue(accountRecord.getDefault());
    }
}
