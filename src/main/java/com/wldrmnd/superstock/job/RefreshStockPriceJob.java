package com.wldrmnd.superstock.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.wldrmnd.superstock.domain.tables.records.StockRecord;
import com.wldrmnd.superstock.request.stock.FindStockRequest;
import com.wldrmnd.superstock.request.stock.price.CreateStockPriceRequest;
import com.wldrmnd.superstock.service.stock.StockPriceService;
import com.wldrmnd.superstock.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RefreshStockPriceJob {

    private static final List<String> ACTUAL_STOCKS = List.of("AAPL", "GOOG", "AMZN", "TSLA", "FB", "NFLX");
    private static final String STOCK_URL = "https://financialmodelingprep.com/api/v3/quote-short/";

    @Value("${nasdaq.api.key}")
    private String apiKey;

    private final StockService stockService;
    private final StockPriceService stockPriceService;
    private final RestTemplate restTemplate;

    @SneakyThrows
    @Async
    @Scheduled(fixedRate = 120000)
    public void refreshStockPrice() {
        for (String stock : ACTUAL_STOCKS) {
            String stockPriceUrl = STOCK_URL + stock + "?apikey=" + apiKey;
            String stockPriceJson = restTemplate.getForEntity(stockPriceUrl, String.class).getBody();
            ObjectMapper objectMapper = new JsonMapper();
            String stockPrice = objectMapper.readTree(stockPriceJson).get(0).get("price").asText();

            StockRecord stockRecord = stockService.find(FindStockRequest.builder()
                    .sign(stock)
                    .build()
            ).stream().findFirst().get();

            stockPriceService.create(CreateStockPriceRequest.builder()
                    .price(new BigDecimal(stockPrice).setScale(2).round(MathContext.DECIMAL32))
                    .stockId(stockRecord.getId())
                    .build());
        }
    }
}
