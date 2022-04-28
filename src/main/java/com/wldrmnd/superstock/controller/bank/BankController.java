package com.wldrmnd.superstock.controller.bank;

import com.wldrmnd.superstock.mapper.bank.BankMapper;
import com.wldrmnd.superstock.model.bank.Bank;
import com.wldrmnd.superstock.request.bank.CreateBankRequest;
import com.wldrmnd.superstock.request.bank.FindBankRequest;
import com.wldrmnd.superstock.service.bank.BankService;
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

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bank> find(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "false") boolean loadAllOnEmptyCriteria
    ) {
        return bankService.find(FindBankRequest.builder()
                .id(id)
                .name(name)
                .findAllOnEmptyCriteria(loadAllOnEmptyCriteria)
                .build()
        ).stream().map(bankMapper::toModel).toList();
    }

    @PostMapping("/create")
    public Bank create(@RequestBody CreateBankRequest request) {
        return bankMapper.toModel(bankService.create(request));
    }
}
