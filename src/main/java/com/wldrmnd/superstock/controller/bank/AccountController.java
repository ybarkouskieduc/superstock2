package com.wldrmnd.superstock.controller.bank;

import com.wldrmnd.superstock.mapper.bank.AccountMapper;
import com.wldrmnd.superstock.model.bank.Account;
import com.wldrmnd.superstock.request.account.CreateAccountRequest;
import com.wldrmnd.superstock.request.account.FindAccountRequest;
import com.wldrmnd.superstock.request.account.UpdateAccountRequest;
import com.wldrmnd.superstock.service.bank.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/find")
    public List<Account> find(FindAccountRequest request) {
        return accountService.find(request).stream().map(accountMapper::toModel).toList();
    }

    @PostMapping("/create")
    public Account create(CreateAccountRequest request) {
        return accountMapper.toModel(accountService.create(request));
    }

    @PutMapping("/update")
    public Account update(UpdateAccountRequest request) {
        return accountMapper.toModel(accountService.update(request));
    }
}