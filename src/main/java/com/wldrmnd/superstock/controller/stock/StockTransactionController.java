package com.wldrmnd.superstock.controller.stock;

import com.wldrmnd.superstock.domain.enums.StockTransactionGoal;
import com.wldrmnd.superstock.mapper.stock.StockTransactionMapper;
import com.wldrmnd.superstock.model.stock.StockTransaction;
import com.wldrmnd.superstock.request.stock.transaction.FindStockTransactionRequest;
import com.wldrmnd.superstock.service.stock.StockTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock/transaction")
public class StockTransactionController {

    private final StockTransactionService stockTransactionService;
    private final StockTransactionMapper stockTransactionMapper;

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public StockTransaction find(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long stockId,
            @RequestParam(required = false) Long stockPriceId,
            @RequestParam(required = false) StockTransactionGoal stockTransactionGoal
    ) {
        return stockTransactionMapper.toModel(stockTransactionService.find(FindStockTransactionRequest.builder()
                .id(id)
                .stockId(stockId)
                .stockPriceId(stockPriceId)
                .userId(userId)
                .goal(stockTransactionGoal)
                .build()
        ).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Stock transaction is not found.")));
    }
}
