package com.wldrmnd.superstock.controller.bank;

import com.wldrmnd.superstock.mapper.bank.BankMapper;
import com.wldrmnd.superstock.model.bank.Bank;
import com.wldrmnd.superstock.request.bank.CreateBankRequest;
import com.wldrmnd.superstock.request.bank.FindBankRequest;
import com.wldrmnd.superstock.service.bank.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank")
public class BankController {

    private final BankService bankService;
    private final BankMapper bankMapper;

    @GetMapping
    public List<Bank> findAll() {
        return bankService.find(FindBankRequest.builder()
                .findAllOnEmptyCriteria(true)
                .build()
        ).stream().map(bankMapper::toModel).toList();
    }

    @GetMapping("/find")
    public List<Bank> find(FindBankRequest request) {
        return bankService.find(request).stream().map(bankMapper::toModel).toList();
    }

    @PostMapping("/create")
    public Bank create(CreateBankRequest request) {
        return bankMapper.toModel(bankService.create(request));
    }
}