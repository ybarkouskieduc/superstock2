package com.wldrmnd.superstock.controller.stock;

import com.wldrmnd.superstock.mapper.stock.StockMapper;
import com.wldrmnd.superstock.model.stock.Stock;
import com.wldrmnd.superstock.request.stock.CreateStockRequest;
import com.wldrmnd.superstock.request.stock.FindStockRequest;
import com.wldrmnd.superstock.service.stock.StockService;
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
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;
    private final StockMapper stockMapper;

    @GetMapping
    public List<Stock> findAll() {
        return stockService.findAll().stream().map(stockMapper::toModel).toList();
    }

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public Stock findById(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sign,
            @RequestParam(required = false, defaultValue = "false") boolean loadAllOnEmptyCriteria
    ) {
        return stockMapper.toModel(stockService.find(FindStockRequest.builder()
                .id(id)
                .name(name)
                .sign(sign)
                .findAllOnEmptyCriteria(loadAllOnEmptyCriteria)
                .build()
        ).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Stock is not exists.")));
    }

    @PostMapping("/create")
    public Stock create(@RequestBody CreateStockRequest request) {
        return stockMapper.toModel(stockService.create(request));
    }
}
