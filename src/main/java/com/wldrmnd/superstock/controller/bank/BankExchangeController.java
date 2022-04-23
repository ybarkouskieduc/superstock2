package com.wldrmnd.superstock.controller.bank;

import com.wldrmnd.superstock.mapper.bank.BankExchangeMapper;
import com.wldrmnd.superstock.model.bank.BankExchange;
import com.wldrmnd.superstock.request.bank.exchange.CreateBankExchangeRequest;
import com.wldrmnd.superstock.request.bank.exchange.FindBankExchangeRequest;
import com.wldrmnd.superstock.service.bank.BankExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank/exchange")
public class BankExchangeController {

    private final BankExchangeService bankExchangeService;
    private final BankExchangeMapper bankExchangeMapper;

    @GetMapping
    public List<BankExchange> findAll() {
        return bankExchangeService.find(FindBankExchangeRequest.builder()
                .findAllOnEmptyCriteria(true)
                .build()
        ).stream().map(bankExchangeMapper::toModel).toList();
    }

    @GetMapping("/find")
    public List<BankExchange> find(FindBankExchangeRequest request) {
        return bankExchangeService.find(request).stream().map(bankExchangeMapper::toModel).toList();
    }

    @PostMapping("/create")
    public BankExchange create(CreateBankExchangeRequest request) {
        return bankExchangeMapper.toModel(bankExchangeService.create(request));
    }
}
