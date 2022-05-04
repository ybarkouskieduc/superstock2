package com.wldrmnd.superstock.service;

import com.wldrmnd.superstock.domain.enums.StockTransactionGoal;
import com.wldrmnd.superstock.domain.tables.records.AccountRecord;
import com.wldrmnd.superstock.domain.tables.records.BankExchangeRecord;
import com.wldrmnd.superstock.domain.tables.records.StockAccountRecord;
import com.wldrmnd.superstock.domain.tables.records.StockPriceRecord;
import com.wldrmnd.superstock.domain.tables.records.StockTransactionRecord;
import com.wldrmnd.superstock.exception.BankExchangeNotFoundException;
import com.wldrmnd.superstock.exception.NotEnoughMoneyOnAccountExistsException;
import com.wldrmnd.superstock.exception.NotEnoughStockToSellException;
import com.wldrmnd.superstock.exception.StockPriceIsNotExistsException;
import com.wldrmnd.superstock.exception.UserAccountNotFoundException;
import com.wldrmnd.superstock.jooq.stock.ExchangeStockRequestJooqRepository;
import com.wldrmnd.superstock.model.bank.Currency;
import com.wldrmnd.superstock.model.stock.StockProfit;
import com.wldrmnd.superstock.request.account.FindAccountRequest;
import com.wldrmnd.superstock.request.account.UpdateAccountRequest;
import com.wldrmnd.superstock.request.bank.exchange.CreateBankExchangeTransactionRequest;
import com.wldrmnd.superstock.request.bank.exchange.FindBankExchangeRequest;
import com.wldrmnd.superstock.request.stock.CalculateStockProfitRequest;
import com.wldrmnd.superstock.request.stock.ExchangeStockRequest;
import com.wldrmnd.superstock.request.stock.FindStockRequest;
import com.wldrmnd.superstock.request.stock.account.CreateStockAccountRequest;
import com.wldrmnd.superstock.request.stock.account.FindStockAccountRequest;
import com.wldrmnd.superstock.request.stock.account.UpdateStockAccountRequest;
import com.wldrmnd.superstock.request.stock.price.FindStockPriceRequest;
import com.wldrmnd.superstock.request.stock.transaction.CreateStockTransactionRequest;
import com.wldrmnd.superstock.request.stock.transaction.FindStockTransactionRequest;
import com.wldrmnd.superstock.request.user.flow.ExchangeCurrencyRequest;
import com.wldrmnd.superstock.service.bank.AccountService;
import com.wldrmnd.superstock.service.bank.BankExchangeService;
import com.wldrmnd.superstock.service.bank.BankExchangeTransactionService;
import com.wldrmnd.superstock.service.stock.StockAccountService;
import com.wldrmnd.superstock.service.stock.StockPriceService;
import com.wldrmnd.superstock.service.stock.StockService;
import com.wldrmnd.superstock.service.stock.StockTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFlowService {

    private final AccountService accountService;
    private final StockService stockService;
    private final BankExchangeService bankExchangeService;
    private final BankExchangeTransactionService bankExchangeTransactionService;
    private final StockAccountService stockAccountService;
    private final StockPriceService stockPriceService;
    private final StockTransactionService stockTransactionService;
    private final ExchangeStockRequestJooqRepository exchangeStockRequestJooqRepository;

    public AccountRecord exchangeCurrency(ExchangeCurrencyRequest request) {
        log.info(
                "Client [{}] wants to sell {}{} to {} in bank with id [{}].",
                request.getUserId(),
                request.getAmount(),
                request.getCurrencyIn(),
                request.getCurrencyOut(),
                request.getBankId()
        );

        // check for available currency in account
        AccountRecord accountFrom = accountService.find(
                FindAccountRequest.builder()
                        .userId(request.getUserId())
                        .currency(request.getCurrencyIn())
                        .build()
                ).stream().findFirst()
                .orElseThrow(() -> new UserAccountNotFoundException(request.getUserId(), request.getCurrencyIn()));
        log.info("Found {}{} in [{}] user [{}] account.", accountFrom.getAmount(), accountFrom.getCurrency(), accountFrom.getUserId(), accountFrom.getId());

        // check for available currency out account
        AccountRecord accountTo = accountService.find(
                        FindAccountRequest.builder()
                                .userId(request.getUserId())
                                .currency(request.getCurrencyOut())
                                .build()
                ).stream().findFirst()
                .orElseThrow(() -> new UserAccountNotFoundException(request.getUserId(), request.getCurrencyOut()));
        log.info("Found {}{} in [{}] user [{}] account.", accountTo.getAmount(), accountTo.getCurrency(), accountTo.getUserId(), accountTo.getId());

        // check for bank exchange rate
        BankExchangeRecord bankExchangeRecord = bankExchangeService.find(
                FindBankExchangeRequest.builder()
                        .bankId(request.getBankId())
                        .currencyIn(request.getCurrencyIn())
                        .currencyOut(request.getCurrencyOut())
                        .lastExchangeRate(true)
                        .build()
                ).stream().findFirst()
                .orElseThrow(() -> new BankExchangeNotFoundException(request.getBankId(), request.getCurrencyIn(), request.getCurrencyOut()));
        log.info("Bank [{}] exchange rate: {}, fee: {}", bankExchangeRecord.getBankId(), bankExchangeRecord.getRate(), bankExchangeRecord.getFee());

        // check for available amount in currency 'in' account
        if (accountFrom.getAmount().compareTo(request.getAmount().add(bankExchangeRecord.getFee())) < 0) {
            throw new NotEnoughMoneyOnAccountExistsException(
                    request.getUserId(),
                    accountFrom.getCurrency(),
                    request.getAmount().add(bankExchangeRecord.getFee()),
                    accountFrom.getAmount()
            );
        }

        // process and make transaction
        BigDecimal calculatedMoneyResult = request.getAmount().multiply(bankExchangeRecord.getRate());
        log.info(
                "Processing {}{} into {} user [{}] account...",
                calculatedMoneyResult,
                request.getCurrencyOut(),
                accountTo.getCurrency(),
                accountTo.getUserId()
        );

        // minus from account
        AccountRecord updatedFromAccount = accountService.update(UpdateAccountRequest.builder()
                .userId(accountFrom.getUserId())
                .amount(accountFrom.getAmount().subtract(request.getAmount()))
                .currency(request.getCurrencyIn())
                .build()
        );
        log.info("Found {}{} in user [{}] account [{}].", updatedFromAccount.getAmount(), updatedFromAccount.getCurrency(), updatedFromAccount.getUserId(), updatedFromAccount.getId());

        AccountRecord updatedToAccount = accountService.update(UpdateAccountRequest.builder()
                .userId(accountFrom.getUserId())
                .amount(accountTo.getAmount().add(calculatedMoneyResult))
                .currency(request.getCurrencyOut())
                .build()
        );
        log.info("Found {}{} in user [{}] account [{}].", updatedToAccount.getAmount(), updatedToAccount.getCurrency(), updatedToAccount.getUserId(), updatedToAccount.getId());

        // add note to transaction
        bankExchangeTransactionService.create(CreateBankExchangeTransactionRequest.builder()
                .userId(request.getUserId())
                .bankId(request.getBankId())
                .currencyIn(request.getCurrencyIn())
                .currencyOut(request.getCurrencyOut())
                .amountIn(request.getAmount())
                .amountOut(calculatedMoneyResult)
                .rate(bankExchangeRecord.getRate())
                .build()
        );

        return updatedToAccount;
    }

    public void scheduleBuyStock(ExchangeStockRequest request) {
        log.info(
                "Client [{}] wants to buy {} [{}] stock when USD price by stock will be under {}.",
                request.getUserId(),
                request.getAmount(),
                request.getStockId(),
                request.getDesiredPriceInUSD()
        );
        exchangeStockRequestJooqRepository.create(request);
    }

    public StockAccountRecord buyStocks(ExchangeStockRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NotEnoughMoneyOnAccountExistsException(
                    request.getUserId(),
                    Currency.USD.name(),
                    BigDecimal.ZERO,
                    BigDecimal.ZERO
            );
        }

        StockAccountRecord stockAccount = findStockAccount(request, true);

        StockPriceRecord latestStockPrice = getLatestStockPrice(request);

        // calculate stock buy price
        BigDecimal stockBuyPrice = request.getAmount().multiply(latestStockPrice.getPrice());

        // check that user has enough money to buy stock
        AccountRecord usdAccount = accountService.find(
                FindAccountRequest.builder()
                        .userId(request.getUserId())
                        .currency(Currency.USD)
                        .build()
        ).stream().findFirst().orElseThrow(() -> new UserAccountNotFoundException(request.getUserId(), Currency.USD));

        if (usdAccount.getAmount().compareTo(stockBuyPrice) < 0) {
            throw new NotEnoughMoneyOnAccountExistsException(
                    request.getUserId(),
                    Currency.USD.name(),
                    stockBuyPrice,
                    usdAccount.getAmount()
            );
        }

        // update USD account
        accountService.update(UpdateAccountRequest.builder()
                .userId(request.getUserId())
                .currency(Currency.USD)
                .amount(usdAccount.getAmount().subtract(stockBuyPrice))
                .build()
        );

        // update stock account
        StockAccountRecord updatedStockAccount = stockAccountService.update(UpdateStockAccountRequest.builder()
                .userId(request.getUserId())
                .stockId(request.getStockId())
                .amount(stockAccount.getAmount().add(request.getAmount()))
                .build()
        );

        // create stock buy transaction
        stockTransactionService.create(CreateStockTransactionRequest.builder()
                .userId(request.getUserId())
                .stockId(request.getStockId())
                .amount(request.getAmount())
                .goal(StockTransactionGoal.BUY)
                .build()
        );

        return updatedStockAccount;
    }

    public AccountRecord sellStock(ExchangeStockRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NotEnoughStockToSellException(
                    request.getUserId(), request.getStockId(),
                    request.getAmount(), BigDecimal.ZERO
            );
        }

        // check for available stock account
        StockAccountRecord stockAccount = findStockAccount(request, false);

        // check that user has enough stock to buy USD
        if (stockAccount.getAmount().compareTo(request.getAmount()) < 0) {
            throw new NotEnoughStockToSellException(
                    request.getUserId(), request.getStockId(),
                    request.getAmount(), stockAccount.getAmount()
            );
        }

        // check for latest stock price
        StockPriceRecord latestStockPrice = getLatestStockPrice(request);

        // calculate stock sell price
        BigDecimal stockSellPriceInUSD = request.getAmount().multiply(latestStockPrice.getPrice());

        AccountRecord usdAccount = accountService.find(
                FindAccountRequest.builder()
                        .userId(request.getUserId())
                        .currency(Currency.USD)
                        .build()
        ).stream().findFirst().orElseThrow(() -> new UserAccountNotFoundException(request.getUserId(), Currency.USD));

        // update stock account
        stockAccountService.update(UpdateStockAccountRequest.builder()
                .userId(request.getUserId())
                .stockId(request.getStockId())
                .amount(stockAccount.getAmount().subtract(request.getAmount()))
                .build()
        );

        // update USD account
        AccountRecord updatedUsdAccount = accountService.update(UpdateAccountRequest.builder()
                .userId(request.getUserId())
                .currency(Currency.USD)
                .amount(usdAccount.getAmount().add(stockSellPriceInUSD))
                .build()
        );

        // create stock sell transaction
        stockTransactionService.create(CreateStockTransactionRequest.builder()
                .userId(request.getUserId())
                .stockId(request.getStockId())
                .amount(request.getAmount())
                .goal(StockTransactionGoal.SELL)
                .build()
        );

        return updatedUsdAccount;
    }

    public StockProfit calculateStockProfit(CalculateStockProfitRequest request) {
        String stockName = stockService.find(
                FindStockRequest.builder()
                        .id(request.getStockId()).build()
        ).stream().findFirst().get().getName();

        // get all buy transactions
        BigDecimal totalBuyAmount = stockTransactionService.find(FindStockTransactionRequest.builder()
                .goal(StockTransactionGoal.BUY)
                .stockId(request.getStockId())
                .userId(request.getUserId())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .build()
        ).stream().map(it -> it.getAmount().multiply(
                stockPriceService.find(FindStockPriceRequest.builder()
                        .id(it.getStockPriceId())
                        .build()
                ).stream().findFirst().get().getPrice())
        ).reduce(BigDecimal.ZERO, BigDecimal::add);

        log.info("User [{}] total buy amount: {} {}", request.getUserId(), totalBuyAmount, stockName);

        // get all sell transactions
        BigDecimal totalSellAmount = stockTransactionService.find(FindStockTransactionRequest.builder()
                .goal(StockTransactionGoal.SELL)
                .stockId(request.getStockId())
                .userId(request.getUserId())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .build()
        ).stream().map(it -> it.getAmount().multiply(
                stockPriceService.find(FindStockPriceRequest.builder()
                        .id(it.getStockPriceId())
                        .build()
                ).stream().findFirst().get().getPrice())
        ).reduce(BigDecimal.ZERO, BigDecimal::add);

        log.info("User [{}] total sell amount: {} {}", request.getUserId(), totalSellAmount, stockName);

        // get difference between buy and sell
        return StockProfit.builder()
                .profit(totalSellAmount.subtract(totalBuyAmount))
                .stockName(stockName)
                .stockId(request.getStockId())
                .build();
    }
    private StockPriceRecord getLatestStockPrice(ExchangeStockRequest request) {
        return stockPriceService.find(
                FindStockPriceRequest.builder()
                        .stockId(request.getStockId())
                        .lastStockPrice(true)
                        .build()
        ).stream().findFirst().orElseThrow(() -> new StockPriceIsNotExistsException(request.getStockId()));
    }

    private StockAccountRecord findStockAccount(ExchangeStockRequest request, boolean createIfNotExists) {
        Optional<StockAccountRecord> optionalStockAccount = stockAccountService.find(
                FindStockAccountRequest.builder()
                        .stockId(request.getStockId())
                        .userId(request.getUserId())
                        .build()
        ).stream().findFirst();

        if (!createIfNotExists) {
            return optionalStockAccount.orElseThrow(
                    () -> new UserAccountNotFoundException(request.getUserId(), request.getStockId())
            );
        }

        return optionalStockAccount.orElse(
                stockAccountService.create(CreateStockAccountRequest.builder()
                        .stockId(request.getStockId())
                        .userId(request.getUserId())
                        .amount(new BigDecimal("0.00"))
                        .build()
                )
        );
    }
}
