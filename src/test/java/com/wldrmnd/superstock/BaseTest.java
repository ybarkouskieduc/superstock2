package com.wldrmnd.superstock;

import com.oblac.nomen.Nomen;
import com.wldrmnd.superstock.domain.tables.records.BankRecord;
import com.wldrmnd.superstock.domain.tables.records.StockRecord;
import com.wldrmnd.superstock.domain.tables.records.UserRecord;
import com.wldrmnd.superstock.request.bank.CreateBankRequest;
import com.wldrmnd.superstock.request.stock.CreateStockRequest;
import com.wldrmnd.superstock.request.stock.price.CreateStockPriceRequest;
import com.wldrmnd.superstock.request.user.CreateUserRequest;
import com.wldrmnd.superstock.service.bank.BankService;
import com.wldrmnd.superstock.service.stock.StockPriceService;
import com.wldrmnd.superstock.service.stock.StockService;
import com.wldrmnd.superstock.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BaseTest {

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockPriceService stockPriceService;

    @Autowired
    private BankService bankService;

    public List<UserRecord> createRandomUsers(int amount) {
        List<UserRecord> result = new ArrayList<>();
        for (int i = 0; i < amount; i++ ) {
            result.add(
                    userService.createUserAndDefaultAccount(CreateUserRequest.builder()
                            .username(Nomen.randomName())
                            .password(Nomen.randomName(10))
                            .build()
                    )
            );
        }

        return result;
    }

    public List<StockRecord> createRandomStocks(int amount) {
        List<StockRecord> result = new ArrayList<>();
        for (int i = 0; i < amount; i++ ) {
            StockRecord stockRecord = stockService.create(CreateStockRequest.builder()
                    .name(Nomen.randomName())
                    .sign(Nomen.randomName(4).toUpperCase())
                    .description(Nomen.randomName(20))
                    .build()
            );
            stockPriceService.create(
                    CreateStockPriceRequest.builder()
                            .stockId(stockRecord.getId())
                            .price(new BigDecimal("50.00" + i))
                            .build()
            );
            result.add(stockRecord);
        }

        return result;
    }

    public List<BankRecord> createRandomBanks(int amount) {
        List<BankRecord> result = new ArrayList<>();
        for (int i = 0; i < amount; i++ ) {
            result.add(
                    bankService.create(CreateBankRequest.builder()
                                    .name(Nomen.randomName())
                                    .description(Nomen.randomName(20))
                            .build()
                    )
            );
        }

        return result;
    }
}
