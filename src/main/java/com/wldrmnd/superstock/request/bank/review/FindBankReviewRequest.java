package com.wldrmnd.superstock.request.bank.review;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindBankReviewRequest {

    private Long reviewId;
    private Long userId;
    private Long bankId;
}
