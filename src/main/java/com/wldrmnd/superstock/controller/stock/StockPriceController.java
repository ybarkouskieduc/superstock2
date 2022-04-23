package com.wldrmnd.superstockPrice.controller.stockPrice;

import com.wldrmnd.superstock.mapper.stock.StockPriceMapper;
import com.wldrmnd.superstock.model.stock.StockPrice;
import com.wldrmnd.superstock.request.stock.price.CreateStockPriceRequest;
import com.wldrmnd.superstock.request.stock.price.FindStockPriceRequest;
import com.wldrmnd.superstock.service.stock.StockPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock/price")
public class StockPriceController {

    private final StockPriceService stockPriceService;
    private final StockPriceMapper stockPriceMapper;

    @GetMapping("/find")
    public StockPrice findById(@RequestBody FindStockPriceRequest request) {
        return stockPriceMapper.toModel(stockPriceService.find(request).stream().findFirst().get());
    }

    @PostMapping("/create")
    public StockPrice create(@RequestBody CreateStockPriceRequest request) {
        return stockPriceMapper.toModel(stockPriceService.create(request));
    }
}
