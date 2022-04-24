package com.wldrmnd.superstock.controller.stock;

import com.wldrmnd.superstock.exception.StockPriceIsNotExistsException;
import com.wldrmnd.superstock.mapper.stock.StockPriceMapper;
import com.wldrmnd.superstock.model.stock.StockPrice;
import com.wldrmnd.superstock.request.stock.price.CreateStockPriceRequest;
import com.wldrmnd.superstock.request.stock.price.FindStockPriceRequest;
import com.wldrmnd.superstock.service.stock.StockPriceService;
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
@RequestMapping("/api/v1/stock/price")
public class StockPriceController {

    private final StockPriceService stockPriceService;
    private final StockPriceMapper stockPriceMapper;

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StockPrice> findById(
            @RequestParam(required = false) Long stockId,
            @RequestParam(required = false) boolean lastStockPrice
    ) {
        return stockPriceService.find(FindStockPriceRequest.builder()
                .stockId(stockId)
                .lastStockPrice(lastStockPrice)
                .build()
        ).stream().map(stockPriceMapper::toModel).toList();
    }

    @PostMapping("/create")
    public StockPrice create(@RequestBody CreateStockPriceRequest request) {
        return stockPriceMapper.toModel(stockPriceService.create(request));
    }
}
