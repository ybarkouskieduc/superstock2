package com.wldrmnd.superstock.request.bank.review;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateBankReviewRequest {

    private Long bankId;
    private Long userId;
    private Integer rate;
    private String review;
}
