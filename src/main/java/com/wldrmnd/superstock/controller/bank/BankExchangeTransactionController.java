package com.wldrmnd.superstock.controller.bank;

import com.wldrmnd.superstock.mapper.bank.BankExchangeTransactionMapper;
import com.wldrmnd.superstock.model.bank.BankExchangeTransaction;
import com.wldrmnd.superstock.model.bank.Currency;
import com.wldrmnd.superstock.request.bank.exchange.FindBankExchangeTransactionRequest;
import com.wldrmnd.superstock.service.bank.BankExchangeTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank/exchange/transaction")
public class BankExchangeTransactionController {

    private final BankExchangeTransactionService bankExchangeTransactionService;
    private final BankExchangeTransactionMapper bankExchangeTransactionMapper;

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BankExchangeTransaction> find(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long bankId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Currency currencyIn,
            @RequestParam(required = false) Currency currencyOut
    ) {
        return bankExchangeTransactionService.find(FindBankExchangeTransactionRequest.builder()
                .id(id)
                .bankId(bankId)
                .userId(userId)
                .currencyIn(currencyIn)
                .currencyOut(currencyOut)
                .build()
        ).stream().map(bankExchangeTransactionMapper::toModel).toList();
    }
}
