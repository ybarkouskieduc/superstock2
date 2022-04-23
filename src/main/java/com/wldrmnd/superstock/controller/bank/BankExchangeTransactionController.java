package com.wldrmnd.superstock.controller.bank;

import com.wldrmnd.superstock.mapper.bank.BankExchangeTransactionMapper;
import com.wldrmnd.superstock.model.bank.BankExchangeTransaction;
import com.wldrmnd.superstock.request.bank.exchange.FindBankExchangeTransactionRequest;
import com.wldrmnd.superstock.service.bank.BankExchangeTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank/exchange/transaction")
public class BankExchangeTransactionController {

    private final BankExchangeTransactionService bankExchangeTransactionService;
    private final BankExchangeTransactionMapper bankExchangeTransactionMapper;

    @GetMapping("/find")
    public List<BankExchangeTransaction> find(FindBankExchangeTransactionRequest request) {
        return bankExchangeTransactionService.find(request).stream().map(bankExchangeTransactionMapper::toModel).toList();
    }
}
