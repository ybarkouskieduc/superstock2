package com.wldrmnd.superstock.controller.stock;

import com.wldrmnd.superstock.mapper.stock.StockAccountMapper;
import com.wldrmnd.superstock.model.stock.Stock;
import com.wldrmnd.superstock.model.stock.StockAccount;
import com.wldrmnd.superstock.request.stock.account.CreateStockAccountRequest;
import com.wldrmnd.superstock.request.stock.account.FindStockAccountRequest;
import com.wldrmnd.superstock.service.stock.StockAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock/account")
public class StockAccountController {

    private final StockAccountService stockAccountService;
    private final StockAccountMapper stockAccountMapper;

    @GetMapping("/find")
    public StockAccount findById(@RequestBody FindStockAccountRequest request) {
        return stockAccountMapper.toModel(stockAccountService.find(request).stream().findFirst().get());
    }

    @PostMapping("/create")
    public StockAccount create(@RequestBody CreateStockAccountRequest request) {
        return stockAccountMapper.toModel(stockAccountService.create(request));
    }
}
