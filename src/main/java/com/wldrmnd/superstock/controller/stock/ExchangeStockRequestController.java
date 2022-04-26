package com.wldrmnd.superstock.controller.stock;

import com.wldrmnd.superstock.jooq.stock.ExchangeStockRequestJooqRepository;
import com.wldrmnd.superstock.mapper.stock.ExchangeStockRequestMapper;
import com.wldrmnd.superstock.model.stock.ExchangeStockRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock/exchange/request")
public class ExchangeStockRequestController {

    private final ExchangeStockRequestJooqRepository exchangeStockRequestJooqRepository;
    private final ExchangeStockRequestMapper exchangeStockRequestMapper;

    @GetMapping(value = "/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExchangeStockRequestModel> findAllPending(
            @RequestParam(required = true) Long userId
    ) {
        return exchangeStockRequestJooqRepository.findAllPending(userId).stream()
                .map(exchangeStockRequestMapper::toModel).toList();
    }

    @GetMapping(value = "/complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExchangeStockRequestModel> findAllComplete(
            @RequestParam(required = true) Long userId
    ) {
        return exchangeStockRequestJooqRepository.findAllComplete(userId).stream()
                .map(exchangeStockRequestMapper::toModel).toList();
    }
}
