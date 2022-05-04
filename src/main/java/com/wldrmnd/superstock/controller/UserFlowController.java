package com.wldrmnd.superstock.controller;

import com.wldrmnd.superstock.mapper.bank.AccountMapper;
import com.wldrmnd.superstock.mapper.stock.StockAccountMapper;
import com.wldrmnd.superstock.model.bank.Account;
import com.wldrmnd.superstock.model.stock.StockAccount;
import com.wldrmnd.superstock.model.stock.StockProfit;
import com.wldrmnd.superstock.request.stock.CalculateStockProfitRequest;
import com.wldrmnd.superstock.request.stock.ExchangeStockRequest;
import com.wldrmnd.superstock.request.user.flow.ExchangeCurrencyRequest;
import com.wldrmnd.superstock.service.UserFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/flow")
public class UserFlowController {

    private final UserFlowService userFlowService;
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
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date dateFrom,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date dateTo
    ) {
        return userFlowService.calculateStockProfit(CalculateStockProfitRequest.builder()
                .stockId(stockId)
                .userId(userId)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .build()
        );
    }
}
