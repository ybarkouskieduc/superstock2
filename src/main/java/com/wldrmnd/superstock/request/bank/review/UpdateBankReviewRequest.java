package com.wldrmnd.superstock.request.bank.review;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateBankReviewRequest {

    private Long reviewId;

    private Integer rate;
    private String review;
}
