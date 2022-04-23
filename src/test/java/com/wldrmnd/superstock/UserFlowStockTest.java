package com.wldrmnd.superstock;

import com.wldrmnd.superstock.domain.tables.records.AccountRecord;
import com.wldrmnd.superstock.domain.tables.records.StockAccountRecord;
import com.wldrmnd.superstock.domain.tables.records.StockRecord;
import com.wldrmnd.superstock.domain.tables.records.UserRecord;
import com.wldrmnd.superstock.model.bank.Currency;
import com.wldrmnd.superstock.request.account.FindAccountRequest;
import com.wldrmnd.superstock.request.account.UpdateAccountRequest;
import com.wldrmnd.superstock.request.stock.ExchangeStockRequest;
import com.wldrmnd.superstock.request.stock.account.FindStockAccountRequest;
import com.wldrmnd.superstock.request.stock.price.CreateStockPriceRequest;
import com.wldrmnd.superstock.service.UserFlowService;
import com.wldrmnd.superstock.service.bank.AccountService;
import com.wldrmnd.superstock.service.stock.StockAccountService;
import com.wldrmnd.superstock.service.stock.StockPriceService;
import com.wldrmnd.superstock.service.stock.StockTransactionService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserFlowStockTest extends BaseTest {

    @Autowired
    private StockTransactionService stockTransactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserFlowService userFlowService;

    @Autowired
    private StockPriceService stockPriceService;

    @Autowired
    private StockAccountService stockAccountService;

    @Test
    void testUserWantToBuyFirstStock() {
        // GIVEN
        UserRecord user = createRandomUsers(1).get(0);
        accountService.update(UpdateAccountRequest.builder()
                .userId(user.getId())
                .currency(Currency.USD)
                .amount(new BigDecimal(100L))
                .build()
        );

        StockRecord stock = createRandomStocks(1).get(0);

        // WHEN
        StockAccountRecord updatedStockAccount = userFlowService.buyStocks(ExchangeStockRequest.builder()
                .stockId(stock.getId())
                .amount(new BigDecimal("1.00"))
                .userId(user.getId())
                .build()
        );

        // THEN
        assertEquals(0, updatedStockAccount.getAmount().compareTo(new BigDecimal("1.00")));
    }

    @SneakyThrows
    @Test
    void testUserWantToSellFirstStock() {
        // GIVEN
        UserRecord user = createRandomUsers(1).get(0);
        accountService.update(UpdateAccountRequest.builder()
                .userId(user.getId())
                .currency(Currency.USD)
                .amount(new BigDecimal(100L))
                .build()
        );

        StockRecord stock = createRandomStocks(1).get(0);

        BigDecimal stockAmount = new BigDecimal("1.00");

        Thread.sleep(500);
        // WHEN
        // buying first stock for 50$ per stock
        StockAccountRecord updatedStockAccount = userFlowService.buyStocks(ExchangeStockRequest.builder()
                .stockId(stock.getId())
                .amount(stockAmount)
                .userId(user.getId())
                .build()
        );

        // first stock prices increasing by 10$ to 60$ per stock
        stockPriceService.create(CreateStockPriceRequest.builder()
                .stockId(stock.getId())
                .price(new BigDecimal("60.00"))
                .build()
        );

        // selling first stock for 60$
        AccountRecord updatedUSDAccount = userFlowService.sellStock(ExchangeStockRequest.builder()
                .stockId(stock.getId())
                .amount(stockAmount)
                .userId(user.getId())
                .build()
        );

        // THEN
        BigDecimal expectedUsdAccountAmount = new BigDecimal("110.00");
        assertEquals(0, updatedUSDAccount.getAmount().compareTo(expectedUsdAccountAmount));
    }

    @Test
    void testUserWantToBuyFirstStockScheduled() throws InterruptedException {
        // GIVEN
        UserRecord user = createRandomUsers(1).get(0);
        accountService.update(UpdateAccountRequest.builder()
                .userId(user.getId())
                .currency(Currency.USD)
                .amount(new BigDecimal(100L))
                .build()
        );

        StockRecord stock = createRandomStocks(1).get(0);

        // WHEN
        userFlowService.scheduleBuyStock(ExchangeStockRequest.builder()
                .stockId(stock.getId())
                .amount(new BigDecimal("1.00"))
                .userId(user.getId())
                .desiredPriceInUSD(new BigDecimal("40.00"))
                .build()
        );
        stockPriceService.create(CreateStockPriceRequest.builder()
                .price(new BigDecimal("39.00"))
                .stockId(stock.getId())
                .build()
        );

        Thread.sleep(1000);

        // THEN
        StockAccountRecord updatedStockAccount = stockAccountService.find(FindStockAccountRequest.builder()
                .userId(user.getId())
                .stockId(stock.getId())
                .build()
        ).get(0);
        AccountRecord updatedUSDAccount = accountService.find(FindAccountRequest.builder()
                .userId(user.getId())
                .currency(Currency.USD)
                .build()
        ).get(0);

        assertEquals(0, updatedStockAccount.getAmount().compareTo(new BigDecimal("1.00")));
        assertEquals(0, updatedUSDAccount.getAmount().compareTo(new BigDecimal("61.00")));
    }
}
