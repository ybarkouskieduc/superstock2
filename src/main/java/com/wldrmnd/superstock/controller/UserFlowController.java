package com.wldrmnd.superstock.controller;

import com.wldrmnd.superstock.domain.tables.records.StockAccountStatisticRecord;
import com.wldrmnd.superstock.jooq.stock.StockAccountStatisticJooqRepository;
import com.wldrmnd.superstock.mapper.bank.AccountMapper;
import com.wldrmnd.superstock.mapper.stock.StockAccountMapper;
import com.wldrmnd.superstock.model.bank.Account;
import com.wldrmnd.superstock.model.stock.StockAccount;
import com.wldrmnd.superstock.model.stock.StockProfit;
import com.wldrmnd.superstock.request.stock.CalculateStockProfitRequest;
import com.wldrmnd.superstock.request.stock.ExchangeStockRequest;
import com.wldrmnd.superstock.request.stock.FindStockRequest;
import com.wldrmnd.superstock.request.user.flow.ExchangeCurrencyRequest;
import com.wldrmnd.superstock.service.UserFlowService;
import com.wldrmnd.superstock.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/flow")
public class UserFlowController {

    private final UserFlowService userFlowService;
    private final StockService stockService;
    private final StockAccountStatisticJooqRepository stockAccountStatisticJooqRepository;
    private final AccountMapper accountMapper;
    private final StockAccountMapper stockAccountMapper;

    @PostMapping("/exchange/currency")
    public Account exchangeCurrency(@RequestBody ExchangeCurrencyRequest request) {
        return accountMapper.toModel(userFlowService.exchangeCurrency(request));
    }

    @PostMapping("/buy/stock")
    public StockAccount buyStock(@RequestBody ExchangeStockRequest request) {
        return stockAccountMapper.toModel(userFlowService.buyStocks(request));
    }

    @PostMapping("/sell/stock")
    public Account sellStock(@RequestBody ExchangeStockRequest request) {
        return accountMapper.toModel(userFlowService.sellStock(request));
    }

    @PostMapping("/buy/stock/schedule")
    public void scheduleBuyStock(@RequestBody ExchangeStockRequest request) {
        userFlowService.scheduleBuyStock(request);
    }

    @GetMapping("/profit/stock")
    public StockProfit calculateStockProfit(
            @RequestParam Long stockId,
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo
    ) {
        List<StockAccountStatisticRecord> stockAccountStatisticRecords = stockAccountStatisticJooqRepository.find(
                userId,
                stockId,
                dateFrom,
                dateTo
        );

        if (stockAccountStatisticRecords.isEmpty()) {
            return StockProfit.builder().build();
        }

        StockAccountStatisticRecord stockAccountStatisticRecord = stockAccountStatisticRecords.stream()
                .findFirst()
                .get();

        return StockProfit.builder()
                .stockId(stockAccountStatisticRecord.getStockId())
                .profit(stockAccountStatisticRecord.getProfit())
                .stockName(stockService.find(
                        FindStockRequest.builder()
                                .id(stockAccountStatisticRecord.getStockId())
                                .build()
                ).stream().findFirst().get().getName())
                .build();
    }
}
