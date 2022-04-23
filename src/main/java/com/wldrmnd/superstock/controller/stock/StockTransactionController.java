package com.wldrmnd.superstock.controller.stock;

import com.wldrmnd.superstock.mapper.stock.StockTransactionMapper;
import com.wldrmnd.superstock.model.stock.StockTransaction;
import com.wldrmnd.superstock.request.stock.transaction.FindStockTransactionRequest;
import com.wldrmnd.superstock.service.stock.StockTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock/transaction")
public class StockTransactionController {

    private final StockTransactionService stockTransactionService;
    private final StockTransactionMapper stockTransactionMapper;

    @GetMapping("/find")
    public StockTransaction find(@RequestBody FindStockTransactionRequest request) {
        return stockTransactionMapper.toModel(stockTransactionService.find(request).stream().findFirst().get());
    }
}
