package com.wldrmnd.superstock.job;

import com.wldrmnd.superstock.domain.enums.StockTransactionGoal;
import com.wldrmnd.superstock.domain.tables.records.StockAccountRecord;
import com.wldrmnd.superstock.domain.tables.records.StockRecord;
import com.wldrmnd.superstock.domain.tables.records.StockTransactionRecord;
import com.wldrmnd.superstock.jooq.stock.StockAccountStatisticJooqRepository;
import com.wldrmnd.superstock.request.stock.FindStockRequest;
import com.wldrmnd.superstock.request.stock.account.FindStockAccountRequest;
import com.wldrmnd.superstock.request.stock.price.FindStockPriceRequest;
import com.wldrmnd.superstock.request.stock.transaction.FindStockTransactionRequest;
import com.wldrmnd.superstock.request.user.FindUserRequest;
import com.wldrmnd.superstock.service.stock.StockAccountService;
import com.wldrmnd.superstock.service.stock.StockPriceService;
import com.wldrmnd.superstock.service.stock.StockService;
import com.wldrmnd.superstock.service.stock.StockTransactionService;
import com.wldrmnd.superstock.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshStockAccountStatisticJob {

    private final StockAccountService stockAccountService;
    private final StockService stockService;
    private final StockPriceService stockPriceService;
    private final StockTransactionService stockTransactionService;
    private final StockAccountStatisticJooqRepository stockAccountStatisticJooqRepository;

    @Async
    @Scheduled(fixedRate = 30000)
    public void refresh() {
        List<Long> stockIds = stockService.find(FindStockRequest.builder()
                .findAllOnEmptyCriteria(true)
                .build()
        ).stream().map(StockRecord::getId).toList();

        for (Long stockId : stockIds) {
            // get current stock price
            BigDecimal currentStockPrice = stockPriceService.find(FindStockPriceRequest.builder()
                    .stockId(stockId)
                    .lastStockPrice(true)
                    .build()
            ).stream().findFirst().get().getPrice();
            log.info("Refresh account with stock {}, current price: {}$", stockId, currentStockPrice);

            // get all stock account
            List<StockAccountRecord> stockAccountRecords = stockAccountService.find(FindStockAccountRequest.builder()
                    .stockId(stockId)
                    .build()
            );

            for (StockAccountRecord stockAccountRecord : stockAccountRecords) {
                log.info("Account {} from user [{}] has {} stocks", stockAccountRecord.getId(), stockAccountRecord.getUserId(), stockAccountRecord.getAmount());
                BigDecimal currentStockAmount = stockAccountRecord.getAmount();
                BigDecimal moneySpend = BigDecimal.ZERO;
                List<StockTransactionRecord> stockTransactionRecords = stockTransactionService.find(
                        FindStockTransactionRequest.builder()
                                .userId(stockAccountRecord.getUserId())
                                .stockId(stockId)
                                .goal(StockTransactionGoal.BUY)
                                .build());
                Collections.reverse(stockTransactionRecords);
                if (stockTransactionRecords.isEmpty()) {
                    log.info("Stock {} transaction for user {} is empty, continuing...", stockId, stockAccountRecord.getUserId());
                    continue;
                }

                int index = 0;
                // calculate how much money user spend on it
                while (currentStockAmount.compareTo(BigDecimal.ZERO) > 0) {
                    // order by created desc
                    StockTransactionRecord stockTransactionRecord = stockTransactionRecords.get(index++);
                    BigDecimal amount = stockTransactionRecord.getAmount();
                    Long stockPriceId = stockTransactionRecord.getStockPriceId();
                    BigDecimal price = stockPriceService.find(FindStockPriceRequest.builder()
                            .id(stockPriceId)
                            .build()).stream().findFirst().get().getPrice();
                    if (currentStockAmount.compareTo(amount) >= 0) {
                        moneySpend = moneySpend.add(price.multiply(amount));
                        currentStockAmount = currentStockAmount.subtract(amount);
                    } else {
                        moneySpend = moneySpend.add(price.multiply(currentStockAmount));
                        currentStockAmount = BigDecimal.ZERO;
                    }
                }

                // calculate money earned
                BigDecimal profit = stockAccountRecord.getAmount().multiply(currentStockPrice).subtract(moneySpend);
                stockAccountStatisticJooqRepository.create(
                        stockAccountRecord.getUserId(),
                        stockId,
                        profit
                );
                log.info("User {} stock {} profit: {}$", stockAccountRecord.getUserId(), stockId, profit);
            }
        }
    }
}
