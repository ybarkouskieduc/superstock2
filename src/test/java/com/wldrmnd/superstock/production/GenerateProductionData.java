package com.wldrmnd.superstock.production;

import com.wldrmnd.superstock.domain.tables.records.BankRecord;
import com.wldrmnd.superstock.domain.tables.records.UserRecord;
import com.wldrmnd.superstock.model.bank.Currency;
import com.wldrmnd.superstock.request.account.CreateAccountRequest;
import com.wldrmnd.superstock.request.account.UpdateAccountRequest;
import com.wldrmnd.superstock.request.bank.CreateBankRequest;
import com.wldrmnd.superstock.request.bank.exchange.CreateBankExchangeRequest;
import com.wldrmnd.superstock.request.user.CreateUserRequest;
import com.wldrmnd.superstock.service.bank.AccountService;
import com.wldrmnd.superstock.service.bank.BankExchangeService;
import com.wldrmnd.superstock.service.bank.BankExchangeTransactionService;
import com.wldrmnd.superstock.service.bank.BankService;
import com.wldrmnd.superstock.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class GenerateProductionData {

    @Autowired private BankExchangeService bankExchangeService;
    @Autowired private AccountService accountService;
    @Autowired private BankExchangeTransactionService bankExchangeTransactionService;
    @Autowired private UserService userService;
    @Autowired private BankService bankService;

    @Test
    void generate1Users() {
        // generate poor one
        UserRecord poorUser = userService.createUserAndDefaultAccount(
                CreateUserRequest.builder()
                        .username("Yahor Barkouski")
                        .password("awesomesecurity")
                        .build()
        );

        // update USD account
        accountService.update(
                UpdateAccountRequest.builder()
                        .userId(poorUser.getId())
                        .currency(Currency.USD)
                        .amount(new BigDecimal("100.00"))
                        .build()
        );

        // create BYN account
        accountService.create(
                CreateAccountRequest.builder()
                        .userId(poorUser.getId())
                        .currency(Currency.BYN)
                        .amount(new BigDecimal("1500.00"))
                        .build()
        );


        // generate rich one
        UserRecord richUser = userService.createUserAndDefaultAccount(
                CreateUserRequest.builder()
                        .username("Yunona Kirylchuk")
                        .password("awesomesecurity")
                        .build()
        );
        // update USD account
        accountService.update(
                UpdateAccountRequest.builder()
                        .userId(richUser.getId())
                        .currency(Currency.USD)
                        .amount(new BigDecimal("1340.20"))
                        .build()
        );

        // create BYN account
        accountService.create(
                CreateAccountRequest.builder()
                        .userId(richUser.getId())
                        .currency(Currency.BYN)
                        .amount(new BigDecimal("15040.00"))
                        .build()
        );

        // create EUR account
        accountService.create(
                CreateAccountRequest.builder()
                        .userId(richUser.getId())
                        .currency(Currency.EUR)
                        .amount(new BigDecimal("4200.99"))
                        .build()
        );
    }

    @Test
    void generate3Banks() {
        BankRecord superBank = bankService.create(
                CreateBankRequest.builder()
                        .name("Супербанк")
                        .description("Хороший банк для настоящик ребят, отличные курсы и сервис")
                        .country("Швейцария")
                        .build()
        );
        // USD to BYN exchange
        bankExchangeService.create(
                CreateBankExchangeRequest.builder()
                        .bankId(superBank.getId())
                        .currencyIn(Currency.USD)
                        .currencyOut(Currency.BYN)
                        .rate(new BigDecimal("2.9"))
                        .build()
        );
        // BYN to USD exchange
        bankExchangeService.create(
                CreateBankExchangeRequest.builder()
                        .bankId(superBank.getId())
                        .currencyIn(Currency.BYN)
                        .currencyOut(Currency.USD)
                        .rate(new BigDecimal("0.38"))
                        .build()
        );


        BankRecord russianBank = bankService.create(
                CreateBankRequest.builder()
                        .name("Банкнеочень")
                        .description("Не очень хороший банк, но все еще пойдет. Валюты мало, зато RUB - полно!")
                        .country("Россия")
                        .build()
        );
        // USD to BYN exchange
        bankExchangeService.create(
                CreateBankExchangeRequest.builder()
                        .bankId(russianBank.getId())
                        .currencyIn(Currency.USD)
                        .currencyOut(Currency.BYN)
                        .rate(new BigDecimal("2.7"))
                        .build()
        );
        // BYN to USD exchange
        bankExchangeService.create(
                CreateBankExchangeRequest.builder()
                        .bankId(russianBank.getId())
                        .currencyIn(Currency.BYN)
                        .currencyOut(Currency.USD)
                        .rate(new BigDecimal("0.28"))
                        .build()
        );


        BankRecord poorBank = bankService.create(
                CreateBankRequest.builder()
                        .name("Ужасбанк")
                        .description("Комментарии излишни")
                        .country("Беларусь")
                        .build()
        );
        // USD to BYN exchange
        bankExchangeService.create(
                CreateBankExchangeRequest.builder()
                        .bankId(poorBank.getId())
                        .currencyIn(Currency.USD)
                        .currencyOut(Currency.BYN)
                        .rate(new BigDecimal("0.1"))
                        .build()
        );
        // BYN to USD exchange
        bankExchangeService.create(
                CreateBankExchangeRequest.builder()
                        .bankId(poorBank.getId())
                        .currencyIn(Currency.BYN)
                        .currencyOut(Currency.USD)
                        .rate(new BigDecimal("0.15"))
                        .build()
        );
    }
}
