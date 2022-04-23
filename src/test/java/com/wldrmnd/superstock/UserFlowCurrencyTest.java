package com.wldrmnd.superstock;

import com.wldrmnd.superstock.domain.tables.records.AccountRecord;
import com.wldrmnd.superstock.domain.tables.records.BankExchangeTransactionRecord;
import com.wldrmnd.superstock.domain.tables.records.BankRecord;
import com.wldrmnd.superstock.domain.tables.records.UserRecord;
import com.wldrmnd.superstock.model.bank.Currency;
import com.wldrmnd.superstock.request.account.CreateAccountRequest;
import com.wldrmnd.superstock.request.account.UpdateAccountRequest;
import com.wldrmnd.superstock.request.bank.exchange.CreateBankExchangeRequest;
import com.wldrmnd.superstock.request.bank.exchange.FindBankExchangeTransactionRequest;
import com.wldrmnd.superstock.request.user.flow.ExchangeCurrencyRequest;
import com.wldrmnd.superstock.service.bank.AccountService;
import com.wldrmnd.superstock.service.bank.BankExchangeService;
import com.wldrmnd.superstock.service.bank.BankExchangeTransactionService;
import com.wldrmnd.superstock.service.UserFlowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserFlowCurrencyTest extends BaseTest {

    @Autowired private UserFlowService userFlowService;

    @Autowired private AccountService accountService;
    @Autowired private BankExchangeTransactionService bankExchangeTransactionService;
    @Autowired private BankExchangeService bankExchangeService;

    @Test
    void testFirstUserTransactionFlow() {
        // GIVEN
        BankRecord bank = createRandomBanks(1).get(0);
        BigDecimal rate = new BigDecimal("2.81");
        bankExchangeService.create(CreateBankExchangeRequest.builder()
                .bankId(bank.getId())
                .currencyIn(Currency.USD)
                .currencyOut(Currency.BYN)
                .rate(rate)
                .build()
        );

        UserRecord user = createRandomUsers(1).get(0);
        AccountRecord bynAccount = accountService.create(CreateAccountRequest.builder()
                .userId(user.getId())
                .currency(Currency.BYN)
                .amount(new BigDecimal("1.00"))
                .build()
        );
        accountService.update(UpdateAccountRequest.builder()
                        .userId(user.getId())
                        .currency(Currency.USD)
                        .amount(new BigDecimal(100L))
                .build()
        );

        // THEN
        BigDecimal amountIn = new BigDecimal("100.00");
        AccountRecord bynAccountResult = userFlowService.exchangeCurrency(ExchangeCurrencyRequest.builder()
                .userId(user.getId())
                .bankId(bank.getId())
                .amount(amountIn)
                .currencyIn(Currency.USD)
                .currencyOut(Currency.BYN)
                .build()
        );

        BankExchangeTransactionRecord bankExchangeTransaction = bankExchangeTransactionService.find(
                FindBankExchangeTransactionRequest.builder()
                        .userId(user.getId())
                        .bankId(bank.getId())
                        .build()
        ).stream().findFirst().orElseThrow(AssertionError::new);

        assertEquals(bynAccount.getId(), bynAccountResult.getId());
        assertTrue(bynAccount.getAmount().compareTo(bynAccountResult.getAmount()) < 0);
        assertEquals(amountIn, bankExchangeTransaction.getAmountIn());
        assertEquals(Currency.USD.name(), bankExchangeTransaction.getCurrencyIn());
        assertEquals(Currency.BYN.name(), bankExchangeTransaction.getCurrencyOut());
    }
}
