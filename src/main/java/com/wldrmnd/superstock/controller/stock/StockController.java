package com.wldrmnd.superstock.controller.stock;

import com.wldrmnd.superstock.mapper.stock.StockMapper;
import com.wldrmnd.superstock.model.stock.Stock;
import com.wldrmnd.superstock.request.stock.CreateStockRequest;
import com.wldrmnd.superstock.request.stock.FindStockRequest;
import com.wldrmnd.superstock.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;
    private final StockMapper stockMapper;

    @GetMapping
    public List<Stock> findAll() {
        return stockService.findAll().stream().map(stockMapper::toModel).toList();
    }

    @GetMapping("/find")
    public Stock findById(@RequestBody FindStockRequest request) {
        return stockMapper.toModel(stockService.find(request).stream().findFirst().get());
    }

    @PostMapping("/create")
    public Stock create(@RequestBody CreateStockRequest request) {
        return stockMapper.toModel(stockService.create(request));
    }
}
