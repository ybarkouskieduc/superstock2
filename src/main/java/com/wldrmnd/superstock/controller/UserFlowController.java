package com.wldrmnd.superstock.controller;

import com.wldrmnd.superstock.mapper.bank.AccountMapper;
import com.wldrmnd.superstock.mapper.stock.StockAccountMapper;
import com.wldrmnd.superstock.model.bank.Account;
import com.wldrmnd.superstock.model.stock.StockAccount;
import com.wldrmnd.superstock.request.stock.ExchangeStockRequest;
import com.wldrmnd.superstock.request.user.flow.ExchangeCurrencyRequest;
import com.wldrmnd.superstock.service.UserFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
