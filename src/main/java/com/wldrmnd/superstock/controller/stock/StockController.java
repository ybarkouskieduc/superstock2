package com.wldrmnd.superstock.controller.stock;

import com.wldrmnd.superstock.domain.tables.records.StockPriceRecord;
import com.wldrmnd.superstock.mapper.stock.StockMapper;
import com.wldrmnd.superstock.model.stock.Stock;
import com.wldrmnd.superstock.request.stock.CreateStockRequest;
import com.wldrmnd.superstock.request.stock.FindStockRequest;
import com.wldrmnd.superstock.request.stock.price.FindStockPriceRequest;
import com.wldrmnd.superstock.service.stock.StockPriceService;
import com.wldrmnd.superstock.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;
    private final StockPriceService stockPriceService;
    private final StockMapper stockMapper;

    @GetMapping
    public List<Stock> findAll() {
        List<Stock> stocks = stockService.findAll().stream().map(stockMapper::toModel).toList();
        stocks.forEach(it -> {
            Optional<StockPriceRecord> lastStockPrice = stockPriceService.find(FindStockPriceRequest.builder()
                    .stockId(it.getId())
                    .lastStockPrice(true)
                    .build()
            ).stream().findFirst();

            lastStockPrice.ifPresentOrElse(
                    price -> it.setCurrentStockPrice(price.getPrice()),
                    () -> it.setCurrentStockPrice(new BigDecimal("0.00"))
            );
        });

        return stocks;
    }

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public Stock find(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sign,
            @RequestParam(required = false, defaultValue = "false") boolean loadAllOnEmptyCriteria
    ) {
        Stock stock = stockMapper.toModel(stockService.find(FindStockRequest.builder()
                .id(id)
                .name(name)
                .sign(sign)
                .findAllOnEmptyCriteria(loadAllOnEmptyCriteria)
                .build()
        ).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Stock is not exists.")));

        Optional<StockPriceRecord> lastStockPrice = stockPriceService.find(FindStockPriceRequest.builder()
                .stockId(stock.getId())
                .lastStockPrice(true)
                .build()
        ).stream().findFirst();

        lastStockPrice.ifPresentOrElse(
                price -> stock.setCurrentStockPrice(price.getPrice()),
                () -> stock.setCurrentStockPrice(new BigDecimal("0.00"))
        );
        return stock;
    }

    @PostMapping("/create")
    public Stock create(@RequestBody CreateStockRequest request) {
        return stockMapper.toModel(stockService.create(request));
    }
}
