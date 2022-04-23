package com.wldrmnd.superstock.controller.bank;

import com.wldrmnd.superstock.mapper.bank.BankReviewMapper;
import com.wldrmnd.superstock.model.bank.BankReview;
import com.wldrmnd.superstock.request.bank.review.CreateBankReviewRequest;
import com.wldrmnd.superstock.request.bank.review.FindBankReviewRequest;
import com.wldrmnd.superstock.request.bank.review.UpdateBankReviewRequest;
import com.wldrmnd.superstock.service.bank.BankReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank/review")
public class BankReviewController {

    private final BankReviewService bankReviewService;
    private final BankReviewMapper bankReviewMapper;
    
    @GetMapping("/find")
    public List<BankReview> find(FindBankReviewRequest request) {
        return bankReviewService.find(request).stream().map(bankReviewMapper::toModel).toList();
    }

    @PostMapping("/create")
    public BankReview create(CreateBankReviewRequest request) {
        return bankReviewMapper.toModel(bankReviewService.create(request));
    }

    @PutMapping("/update")
    public BankReview update(UpdateBankReviewRequest request) {
        return bankReviewMapper.toModel(bankReviewService.update(request));
    }
}
