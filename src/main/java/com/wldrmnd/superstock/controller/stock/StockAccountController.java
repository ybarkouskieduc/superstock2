package com.wldrmnd.superstock.controller.stock;

import com.wldrmnd.superstock.mapper.stock.StockAccountMapper;
import com.wldrmnd.superstock.model.stock.StockAccount;
import com.wldrmnd.superstock.request.stock.account.CreateStockAccountRequest;
import com.wldrmnd.superstock.request.stock.account.FindStockAccountRequest;
import com.wldrmnd.superstock.service.stock.StockAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock/account")
public class StockAccountController {

    private final StockAccountService stockAccountService;
    private final StockAccountMapper stockAccountMapper;

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StockAccount> findById(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long stockId,
            @RequestParam(required = false) Long userId
    ) {
        return stockAccountService.find(FindStockAccountRequest.builder()
                .id(id)
                .stockId(stockId)
                .userId(userId)
                .build()
        ).stream().map(stockAccountMapper::toModel).toList();
    }

    @PostMapping("/create")
    public StockAccount create(@RequestBody CreateStockAccountRequest request) {
        return stockAccountMapper.toModel(stockAccountService.create(request));
    }
}
