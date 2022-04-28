package com.wldrmnd.superstock.controller.bank;

import com.wldrmnd.superstock.mapper.bank.AccountMapper;
import com.wldrmnd.superstock.model.bank.Account;
import com.wldrmnd.superstock.model.bank.Currency;
import com.wldrmnd.superstock.request.account.CreateAccountRequest;
import com.wldrmnd.superstock.request.account.FindAccountRequest;
import com.wldrmnd.superstock.request.account.UpdateAccountRequest;
import com.wldrmnd.superstock.service.bank.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping
    public List<Account> findAll() {
        return accountService.find(FindAccountRequest.builder()
                .findAllOnEmptyCriteria(true)
                .build()
        ).stream().map(accountMapper::toModel).toList();
    }

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> find(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Currency currency,
            @RequestParam(required = false, defaultValue = "false") boolean loadAllOnEmptyCriteria
    ) {
        return accountService.find(FindAccountRequest.builder()
                .id(id)
                .userId(userId)
                .currency(currency)
                .findAllOnEmptyCriteria(loadAllOnEmptyCriteria)
                .build()).stream().map(accountMapper::toModel).toList();
    }

    @PostMapping("/create")
    public Account create(@RequestBody CreateAccountRequest request) {
        return accountMapper.toModel(accountService.create(request));
    }

    @PutMapping("/update")
    public Account update(@RequestBody UpdateAccountRequest request) {
        return accountMapper.toModel(accountService.update(request));
    }
}
